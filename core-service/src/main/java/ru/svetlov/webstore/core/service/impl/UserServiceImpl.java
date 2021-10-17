package ru.svetlov.webstore.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.core.domain.SecurityPermission;
import ru.svetlov.webstore.core.domain.SecurityRole;
import ru.svetlov.webstore.core.domain.User;
import ru.svetlov.webstore.core.domain.UserInfo;
import ru.svetlov.webstore.api.dto.UserDto;
import ru.svetlov.webstore.core.repository.SecurityRoleRepository;
import ru.svetlov.webstore.core.repository.UserRepository;
import ru.svetlov.webstore.core.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final Validator validator;
    private final UserRepository userRepository;
    private final SecurityRoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(Validator validator,
                           UserRepository userRepository,
                           SecurityRoleRepository roleRepository,
                           @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserRolesAndPermissionsByUsername(username);
        Set<String> permissions = extractPermissionsAndRoles(user);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
    }

    private Set<String> extractPermissionsAndRoles(User user) {
        Set<String> permissions = new HashSet<>();
        permissions.addAll(user.getRoles().stream()
                .map(SecurityRole::getRole)
                .collect(Collectors.toSet()));
        permissions.addAll(user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(SecurityPermission::getPermission)
                .collect(Collectors.toSet()));
        return permissions;
    }


    @Override
    public User getUserRolesAndPermissionsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("user %s not found", username)));
        user.setRoles(getUserRoles(user));
        return user;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        user.setRoles(getUserRoles(user));
        return Optional.of(user);
    }

    @Override
    public User createUserFromDto(UserDto dto) {
        if (userRepository.countByUsername(dto.getUsername()) != 0) {
            throw new IllegalArgumentException("Username " + dto.getUsername() + " already registered");
        }
        User user = new User(dto.getUsername(), passwordEncoder.encode(dto.getPassword()));
        user.setUserInfo(new UserInfo(dto.getFirstName(), dto.getLastName(), dto.getMiddleName(), dto.getEmail()));
        SecurityRole userRole = roleRepository.findByRoleIgnoreCase("role_user");
        user.setRoles(List.of(userRole));
        user = userRepository.save(user);
        return user;
    }

    @Override
    public Page<User> getUsers(int pageNumber, int pageCount) {
        List<User> usersList = new ArrayList<>();
        userRepository.findAll(PageRequest.of(pageNumber, pageCount)).stream()
                .map(User::getUsername)
                .forEach(u -> usersList.add(userRepository.findByUsername(u).orElseThrow()));
        usersList.forEach(user -> user.setRoles(getUserRoles(user)));
        return new PageImpl<>(usersList);
    }

    @Override
    public Optional<User> findUserByName(String username) {
        return userRepository.findUserByUsername(username);
    }

    private Collection<SecurityRole> getUserRoles(User user) {
        return roleRepository.findAllByIdIn(user.getRoles().stream().map(SecurityRole::getId).collect(Collectors.toSet()));
    }

    private void throwIfNotValid(Set<ConstraintViolation<User>> violations) {
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations
                    .stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")));
        }

    }
}
