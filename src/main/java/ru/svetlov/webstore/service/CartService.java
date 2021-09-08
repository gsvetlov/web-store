package ru.svetlov.webstore.service;

import ru.svetlov.webstore.dto.CartItemDto;
import ru.svetlov.webstore.util.cart.Cart;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {

    Cart getCart();

    void addItem(Long productId);

    void removeItem(Long productId);

    void deleteItem(Long productId);

    void clear();
}
