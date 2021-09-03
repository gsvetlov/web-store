package ru.svetlov.webstore.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.svetlov.webstore.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/dao")
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;

    @GetMapping("/auth_page")
    public String getAuthPage() {
        return "Authenticated";
    }

    @GetMapping("/admin/users") // сюда пустит sa и admin
    public String getAdminUsers() {
        return "admin/users";
    }

    @GetMapping("/admin/contents") // сюда пустит sa и content-manager, admin не зайдёт
    public String getAdminContents() {
        return "admin/contents";
    }

    @GetMapping("/user_info")
    public String getUserInfo(Principal principal) {
        return userService.findByUserName(principal.getName())
                .orElseThrow(()-> new UsernameNotFoundException("User not found: " + principal.getName()))
                .toString();
    }
}
