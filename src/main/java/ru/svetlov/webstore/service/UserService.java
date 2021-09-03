package ru.svetlov.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.svetlov.webstore.domain.Role;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.domain.UserPermission;
import ru.svetlov.webstore.repository.UserRepository;

import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    public Optional<User> findByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Transactional
    public Optional<User> findUserWithRolesByUsername(String username) {
        User user = userRepository.findUserWithAllRolesAndPermissionsByUsername(username).orElseThrow();
        return Optional.of(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserWithRolesByUsername(username).orElseThrow(() // findByUserName(username).orElseThrow(
                -> new UsernameNotFoundException("User not found: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Stream<SimpleGrantedAuthority> permissions = roles.stream()
                .flatMap(role -> role.getPermissionGroups().stream()
                        .flatMap(group -> group.getPermissions().stream()
                                .map(UserPermission::getPermission)))
                .distinct()
                .map(SimpleGrantedAuthority::new); // достаем правила доступа из групп доступа из ролей пользователя
        Stream<SimpleGrantedAuthority> authorities = roles.stream()
                .map(Role::getRole)
                .map(SimpleGrantedAuthority::new); // достаём сами роли пользователя
        return Stream.concat(authorities, permissions).collect(Collectors.toList());
    }
}
