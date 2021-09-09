package ru.svetlov.webstore.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.dto.UserDto;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User getUserRolesAndPermissionsByUsername(String username);
    Optional<User> getUserById(Long id);
    User createUserFromDto(UserDto dto);
    Page<User> getUsers(int pageNumber, int pageCount);
}
