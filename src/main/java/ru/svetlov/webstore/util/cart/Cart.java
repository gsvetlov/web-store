package ru.svetlov.webstore.util.cart;

import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.dto.CartItemDto;

import java.math.BigDecimal;
import java.util.Collection;

public interface Cart {
    String getId();

    void addItem(Product product);

    boolean incrementItem(Long productId);

    void decrementItem(Long productId);

    void removeItem(Long productId);

    void clear();

    void merge(Cart other);

    Collection<CartItemDto> getContents();

    BigDecimal getTotal();
}
