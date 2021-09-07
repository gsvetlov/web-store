package ru.svetlov.webstore.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.dto.UserDto;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> getWithRolesAndPermissionsById(Long id);
    Optional<User> getWithInfoById(Long id);
    User createUser(String username, String password);
    User createUserFromDto(UserDto dto);
}
