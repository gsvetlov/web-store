package ru.svetlov.webstore.core.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.api.dto.OrderDetailsDto;
import ru.svetlov.webstore.core.domain.Order;
import ru.svetlov.webstore.core.domain.User;
import ru.svetlov.webstore.core.exception.ResourceNotFoundException;
import ru.svetlov.webstore.core.service.CartService;
import ru.svetlov.webstore.core.service.OrderService;
import ru.svetlov.webstore.core.service.UserService;
import ru.svetlov.webstore.core.util.cart.Cart;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;

    @PutMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDetailsDto dto, Principal principal) {
        User user = userService.getUserRolesAndPermissionsByUsername(principal.getName());
        Cart cart = cartService.create();
        Order order = orderService.createOrder(user, cart, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId, Principal principal ) {
        User user = userService.getUserRolesAndPermissionsByUsername(principal.getName());
        Order order = orderService.getOrderById(orderId, null).orElseThrow(()->new ResourceNotFoundException("Order not found"));
        return new ResponseEntity<>(order, HttpStatus.OK); //TODO: fix JSON binding
    }
}
