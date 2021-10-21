package ru.svetlov.webapp.order.service;

import ru.svetlov.webapp.order.domain.Order;
import ru.svetlov.webstore.api.dtos.OrderDetailsDto;

import java.util.Optional;

public interface OrderService {
    Order createOrder(Long userId, OrderDetailsDto details);

    Optional<Order> getOrderById(Long orderId, Long userId);
}
