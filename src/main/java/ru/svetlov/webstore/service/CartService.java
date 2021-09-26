package ru.svetlov.webstore.service;

import ru.svetlov.webstore.util.cart.Cart;

import java.util.Optional;

public interface CartService {

    Cart create();

    Cart create(String user);

    Optional<Cart> getCartById(String cartId);

    Optional<Cart> getCartByUsername(String username);

    Optional<Cart> getCart(String cartId, String username);

    void addItem(String cartId, Long productId);

    void removeItem(String cartId, Long productId);

    void deleteItem(String cartId, Long productId);

    void clear(String cartId);
}
