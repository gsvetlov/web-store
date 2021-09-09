package ru.svetlov.webstore.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.dto.OrderDetailsDto;
import ru.svetlov.webstore.service.CartService;
import ru.svetlov.webstore.service.OrderService;
import ru.svetlov.webstore.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;

    @PutMapping
    public void createOrder(@RequestBody OrderDetailsDto dto, Principal principal) {
        User user = userService.getUserRolesAndPermissionsByUsername(principal.getName());
        cartService.getCart();
        orderService
    }
}
