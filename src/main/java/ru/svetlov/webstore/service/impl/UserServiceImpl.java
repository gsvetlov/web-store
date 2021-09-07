package ru.svetlov.webstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.domain.SecurityPermission;
import ru.svetlov.webstore.domain.SecurityRole;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.repository.SecurityRoleRepository;
import ru.svetlov.webstore.repository.UserRepository;
import ru.svetlov.webstore.service.UserService;

import javax.validation.Validator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Validator validator;
    private final UserRepository userRepository;
    private final SecurityRoleRepository roleRepository;

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
    public Optional<User> getWithRolesAndPermissionsById(Long id) {
        return userRepository.findById(id);
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
        User user = new User(username, password);
        throwIfNotValid(validator.validate(User.class, user));
        return userRepository.save(user);
    }

    private void throwIfNotValid(Set<ConstraintViolation<User>> violations) {
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations
                    .stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")));
        }

    }
