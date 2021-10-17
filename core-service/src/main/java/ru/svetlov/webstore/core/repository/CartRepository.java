package ru.svetlov.webstore.core.repository;

import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.core.util.cart.Cart;

import java.util.Optional;


@Repository
public interface CartRepository {
    Optional<Cart> findById(String cartId);
    boolean existsById(String cartId);
    void save(Cart cart);
    Cart create();
    Cart create(Long uid);
    Optional<Cart> findByOwnerId(Long uid);
    Long findOwner(Cart cart);
    Cart merge(Cart source, Cart destination);
    void setOwner(Cart cart, Long uid);
    void clear(String cartId);
}
