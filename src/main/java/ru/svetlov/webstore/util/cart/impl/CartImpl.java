package ru.svetlov.webstore.util.cart.impl;

import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.dto.CartItemDto;
import ru.svetlov.webstore.util.cart.Cart;

import java.math.BigDecimal;
import java.util.*;


public class CartImpl implements Cart {
    private static final int INCREMENT_VALUE = 1;
    private static final int DECREMENT_VALUE = -1;
    private final Map<Long, CartItemDto> contents;

    public CartImpl() {
        this.contents = new HashMap<>();
    }

    @Override
    public BigDecimal getTotal() {
        return contents.values().stream().map(CartItemDto::getItemSum).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean incrementItem(Long productId) {
        return changeItem(productId, INCREMENT_VALUE);
    }

    @Override
    public boolean decrementItem(Long productId) {
        return changeItem(productId, DECREMENT_VALUE);
    }

    private boolean changeItem(Long productId, int delta) {
        if (!contents.containsKey(productId)) {
            return false;
        }
        CartItemDto item = contents.get(productId);
        item.changeQuantity(delta);
        if (item.getQuantity() <= 0) {
            removeItem(productId);
        }
        return true;
    }

    @Override
    public void addItem(Product product) {
        CartItemDto item = new CartItemDto(product);
        contents.put(product.getId(), item);
    }

    @Override
    public void removeItem(Long productId) {
        contents.remove(productId);
    }

    @Override
    public void clear() {
        contents.clear();
    }

    @Override
    public Collection<CartItemDto> getContents() {
        return Collections.unmodifiableCollection(contents.values());
    }
}
