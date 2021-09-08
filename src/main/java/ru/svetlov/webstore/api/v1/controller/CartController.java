package ru.svetlov.webstore.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.dto.CartDto;
import ru.svetlov.webstore.util.cart.Cart;
import ru.svetlov.webstore.util.cart.impl.CartImpl;
import ru.svetlov.webstore.dto.CartItemDto;
import ru.svetlov.webstore.service.CartService;
import ru.svetlov.webstore.util.ControllerUtil;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCartContents() {
        Cart cart = cartService.getCart();
        return ResponseEntity.ok(new CartDto(cart.getContents(), cart.getTotal()));
    }

    @GetMapping("/add/{productId}")
    public void addItem(@PathVariable Long productId) {
        cartService.addItem(productId);
    }

    @GetMapping("/remove/{productId}")
    public void removeItem(@PathVariable Long productId) {
        cartService.removeItem(productId);
    }

    @GetMapping("/delete/{productId}")
    public void deleteItem(@PathVariable Long productId) {
        cartService.deleteItem(productId);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clear();
    }
}


