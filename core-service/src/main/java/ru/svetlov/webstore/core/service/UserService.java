package ru.svetlov.webstore.core.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.svetlov.webstore.core.domain.User;
import ru.svetlov.webstore.api.dto.UserDto;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findUserByUsername(String username);
    User getUserRolesAndPermissionsByUsername(String username);
    Optional<User> getUserById(Long id);
    User createUserFromDto(UserDto dto);
    Page<User> getUsers(int pageNumber, int pageCount);
    Optional<User> findUserByName(String username);
}
