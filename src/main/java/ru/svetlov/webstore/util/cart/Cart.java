package ru.svetlov.webstore.util.cart;

import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.dto.CartItemDto;

import java.math.BigDecimal;
import java.util.Collection;

public interface Cart {
    BigDecimal getTotal();

    boolean incrementItem(Long productId);

    boolean decrementItem(Long productId);

    void addItem(Product product);

    void removeItem(Long productId);

    void clear();

    Collection<CartItemDto> getContents();
}
