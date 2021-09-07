package ru.svetlov.webstore.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.dto.UserDto;
import ru.svetlov.webstore.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable Long id) {
        UserDto userDto = new UserDto(userService.getWithRolesAndPermissionsById(id).orElseThrow());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
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
