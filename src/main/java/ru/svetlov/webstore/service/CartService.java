package ru.svetlov.webstore.service;

import ru.svetlov.webstore.dto.CartItemDto;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {

    Map<Long, Integer> getAll();

    void addItem(CartItemDto dto);

    void removeItem(CartItemDto dto);

    void updateItem(CartItemDto dto);

    BigDecimal getTotalSum();
}
