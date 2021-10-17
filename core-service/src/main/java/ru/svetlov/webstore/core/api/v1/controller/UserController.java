package ru.svetlov.webstore.core.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.api.dto.UserDto;
import ru.svetlov.webstore.core.domain.User;
import ru.svetlov.webstore.core.service.UserService;
import ru.svetlov.webstore.core.util.converters.UserConverter;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserConverter converter;
    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize(value = "isFullyAuthenticated()")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id, Principal principal) {
        User user = userService.getUserRolesAndPermissionsByUsername(principal.getName());
        if (user.getId().equals(id) || user.getPermissionsAsStrings().contains("edit-user-info")) {
            return new ResponseEntity<>(converter.map(userService.getUserById(id).orElseThrow()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('edit-user-info')")
    public Page<UserDto> getUsers(@RequestParam(name = "p", defaultValue = "0") int pageNumber,
                                  @RequestParam(name = "c", defaultValue = "10") int pageCount) {
        return userService.getUsers(pageNumber, pageCount).map(converter::map);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserDto dto) {
        return new ResponseEntity<>(converter.map(userService.createUserFromDto(dto)), HttpStatus.CREATED);
    }
}
