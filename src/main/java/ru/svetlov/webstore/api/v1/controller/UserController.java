package ru.svetlov.webstore.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.dto.UserDto;
import ru.svetlov.webstore.service.UserService;

import java.security.Principal;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id, Principal principal) {
        User user = userService.getUserRolesAndPermissionsByUsername(principal.getName());
        if (user.getId().equals(id) || user.getPermissionsAsStrings().contains("edit-user-info")) {
            UserDto userDto = new UserDto(userService.getWithRolesAndPermissionsById(id).orElseThrow());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('edit-user-info')")
    public Page<UserDto> getUsers(@RequestParam(name = "p", defaultValue = "1") int pageNumber,
                                  @RequestParam(name = "c", defaultValue = "10") int pageCount) {
        return userService.getUsers(pageNumber, pageCount).map(UserDto::new);
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        User user = userService.createUserFromDto(dto);
        return new ResponseEntity<>(new UserDto(user), HttpStatus.CREATED);
    }

//    @GetMapping("/create") //TODO: Для тестов, убрать
//    public ResponseEntity<UserDto> createUserByGet(@RequestParam(name = "u") String username,
//                                                   @RequestParam(name = "p") String password) {
//        User user = userService.createUser(username, password);
//        return new ResponseEntity<>(new UserDto(user), HttpStatus.CREATED);
//    }
}
