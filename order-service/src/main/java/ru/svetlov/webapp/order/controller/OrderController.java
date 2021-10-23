package ru.svetlov.webapp.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webapp.order.domain.Order;
import ru.svetlov.webapp.order.service.OrderService;
import ru.svetlov.webstore.api.dtos.OrderDetailsDto;
import ru.svetlov.webstore.api.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createOrder(@RequestHeader(required = false) Long userId,
                            @RequestBody OrderDetailsDto orderDetails) {
        orderService.createOrder(userId, orderDetails);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> getOrder(@RequestHeader Long userId, @PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId, userId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return new ResponseEntity<>(order, HttpStatus.OK); //TODO: map to OrderDto
    }
}
