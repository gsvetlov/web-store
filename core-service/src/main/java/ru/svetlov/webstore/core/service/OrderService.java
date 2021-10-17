package ru.svetlov.webstore.core.service;

import ru.svetlov.webstore.core.domain.Order;
import ru.svetlov.webstore.core.domain.User;
import ru.svetlov.webstore.api.dto.OrderDetailsDto;
import ru.svetlov.webstore.core.util.cart.Cart;

import java.util.Optional;

public interface OrderService {
    Order createOrder(User user, Cart cart, OrderDetailsDto details);

    Optional<Order> getOrderById(Long orderId, User user);
}
