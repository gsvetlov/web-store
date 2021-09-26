package ru.svetlov.webstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.exception.ResourceNotFoundException;
import ru.svetlov.webstore.repository.CartRepository;
import ru.svetlov.webstore.service.CartService;
import ru.svetlov.webstore.service.ProductService;
import ru.svetlov.webstore.service.UserService;
import ru.svetlov.webstore.util.cart.Cart;

import java.util.Optional;

@Primary
@Service
@RequiredArgsConstructor
public class RedisCartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public Cart create() {
        return cartRepository.create();
    }

    @Override
    public Cart create(String username)
    {
        Long id = getUserIdByUsername(username);
        return cartRepository.create(id);
    }

    private Long getUserIdByUsername(String username) {
        return userService.findUserByUsername(username).orElse(User.UNKNOWN).getId(); // TODO: cache results
    }

    @Override
    public Optional<Cart> getCart(String cartId, String username) {
        Optional<Cart> userCart = getCartByUsername(username);
        if (isUserCartSameWithRequested(userCart, cartId)) {
            return userCart;
        }

        Optional<Cart> requestedCart = getCartById(cartId);
        if (canMerge(requestedCart, userCart)) {
            return mergeCarts(requestedCart, userCart);
        }

        if (canSetOwner(requestedCart))
            return setOwner(requestedCart, username);

        return Optional.empty();
    }

    @Override
    public Optional<Cart> getCartByUsername(String username) {
        long userId = getUserIdByUsername(username);
        return cartRepository.findByOwnerId(userId);
    }

    @Override
    public Optional<Cart> getCartById(String cartId) {
        return cartRepository.findById(cartId);
    }

    private boolean isUserCartSameWithRequested(Optional<Cart> userCart, String requestedCartId) {
        return userCart.isPresent() && userCart.get().getId().equals(requestedCartId);
    }

    private boolean canMerge(Optional<Cart> source, Optional<Cart> destination) {
        return canSetOwner(source) && destination.isPresent();
    }

    private boolean canSetOwner(Optional<Cart> cart) {
        return cart.isPresent() && cartRepository.findOwner(cart.get()) == 0;
    }

    private Optional<Cart> mergeCarts(Optional<Cart> source, Optional<Cart> destination) {
        return Optional.of(cartRepository.merge(source.orElseThrow(), destination.orElseThrow()));
    }

    private Optional<Cart> setOwner(Optional<Cart> cart, String username) {
        Long userId = getUserIdByUsername(username);
        cartRepository.setOwner(cart.orElseThrow(), userId);
        return cart;
    }

    @Override
    public void addItem(String cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        addItem(cart, productId);
        cartRepository.save(cart);
    }

    private void addItem(Cart cart, Long productId) {
        if (cart.incrementItem(productId)) {
            return;
        }
        cart.addItem(productService.getById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid product id: " + productId)));
    }

    @Override
    public void removeItem(String cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.decrementItem(productId);
        cartRepository.save(cart);
    }

    @Override
    public void deleteItem(String cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    @Override
    public void clear(String cartId) {
        cartRepository.clear(cartId);
    }
}
