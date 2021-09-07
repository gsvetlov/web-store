package ru.svetlov.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.domain.SecurityPermission;
import ru.svetlov.webstore.domain.SecurityRole;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.domain.UserInfo;
import ru.svetlov.webstore.dto.UserDto;
import ru.svetlov.webstore.repository.SecurityRoleRepository;
import ru.svetlov.webstore.repository.UserRepository;
import ru.svetlov.webstore.service.UserService;

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
        User user = loadUserWithRolesAndPermissions(username);
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


    private User loadUserWithRolesAndPermissions(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("user %s not found", username)));
        user.setRoles(roleRepository.findAllByIdIn(user.getRoles().stream().map(SecurityRole::getId).collect(Collectors.toSet())));
        return user;
    }

    @Override
    public User getUserRolesAndPermissionsByUsername(String username) {
        return loadUserWithRolesAndPermissions(username);
    }

    @Override
    public Optional<User> getWithRolesAndPermissionsById(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        user.setRoles(roleRepository.findAllByIdIn(user.getRoles().stream().map(SecurityRole::getId).collect(Collectors.toSet())));
        return Optional.of(user);
    }

    @Override
    public Optional<User> getWithInfoById(Long id) {
        return Optional.empty();
    }

    @Override
    public User createUser(String username, String password) {
        if (userRepository.countByUsername(username) != 0) {
            throw new IllegalArgumentException("Username " + username + " already registered");
        }
        User user = new User(username, passwordEncoder.encode(password));
        user.setRoles(List.of(roleRepository.findByRoleIgnoreCase("role_user")));
        //throwIfNotValid(validator.validate(user, User.class)); // TODO: fix
        user = userRepository.save(user);
        return getWithRolesAndPermissionsById(user.getId()).orElseThrow();
    }

    @Override
    public User createUserFromDto(UserDto dto) {
        User user = createUser(dto.getUsername(), dto.getPassword());
        //user.setUserInfo(new UserInfo(dto.getFirstName(), dto.getLastName(), dto.getMiddleName(), dto.getEmail())); // TODO: fix org.hibernate.TransientPropertyValueException
        //user = userRepository.save(user);
        return user;
    }

    @Override
    public Page<User> getUsers(int pageNumber, int pageCount) {
        return userRepository.findAll(PageRequest.of(pageNumber, pageCount));
    }

    private void throwIfNotValid(Set<ConstraintViolation<User>> violations) {
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations
                    .stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")));
        }

    }
}
