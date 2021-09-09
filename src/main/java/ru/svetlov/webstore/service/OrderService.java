package ru.svetlov.webstore.service;

import org.springframework.stereotype.Service;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.dto.OrderDetailsDto;
import ru.svetlov.webstore.util.cart.Cart;

@Service
public class OrderService {

    public void createOrder(User user, Cart cart, OrderDetailsDto details) {
        
    }
}
