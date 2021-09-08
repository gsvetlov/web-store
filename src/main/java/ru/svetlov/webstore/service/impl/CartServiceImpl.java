package ru.svetlov.webstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.exception.ResourceNotFoundException;
import ru.svetlov.webstore.util.cart.Cart;
import ru.svetlov.webstore.dto.CartItemDto;
import ru.svetlov.webstore.service.CartService;
import ru.svetlov.webstore.service.ProductService;
import ru.svetlov.webstore.util.cart.impl.CartImpl;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ProductService productService;
    private Cart cart;

    @PostConstruct
    private void init() {
        this.cart = new CartImpl();
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addItem(Long productId) {
        if (cart.incrementItem(productId)) {
            return;
        }
        cart.addItem(productService.getById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid product id: " + productId)));
    }

    @Override
    public void removeItem(Long productId) {
        cart.decrementItem(productId);
    }

    @Override
    public void deleteItem(Long productId) {
        cart.removeItem(productId);
    }

    @Override
    public void clear() {
        cart.clear();
    }

}
