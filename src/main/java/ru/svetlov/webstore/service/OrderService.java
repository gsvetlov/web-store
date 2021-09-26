package ru.svetlov.webstore.service;

import ru.svetlov.webstore.domain.Order;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.dto.OrderDetailsDto;
import ru.svetlov.webstore.util.cart.Cart;

import java.util.Optional;

public interface OrderService {
    Order createOrder(User user, Cart cart, OrderDetailsDto details);

    Optional<Order> getOrderById(Long orderId, User user);
}
