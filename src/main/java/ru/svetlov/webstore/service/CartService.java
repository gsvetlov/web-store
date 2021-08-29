package ru.svetlov.webstore.service;

import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.dto.CartItemDto;

import java.util.Map;

public interface CartService {
    Map<Product, Integer> getAll();

    void addItem(CartItemDto dto);

    void removeItem(CartItemDto dto);

    void updateItem(CartItemDto dto);
}
