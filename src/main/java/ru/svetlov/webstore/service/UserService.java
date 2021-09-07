package ru.svetlov.webstore.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.svetlov.webstore.domain.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> getWithRolesAndPermissionsById(Long id);
    Optional<User> getWithInfoById(Long id);
}
