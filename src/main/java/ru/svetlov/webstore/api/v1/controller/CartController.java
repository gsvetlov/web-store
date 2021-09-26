package ru.svetlov.webstore.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.dto.CartDto;
import ru.svetlov.webstore.exception.BadRequestException;
import ru.svetlov.webstore.exception.ResourceNotFoundException;
import ru.svetlov.webstore.util.cart.Cart;
import ru.svetlov.webstore.service.CartService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCart(Principal principal) {
        Cart cart;
        if (principal == null) {
            cart = cartService.create();
        } else {
            String user = principal.getName();
            cart = cartService.getCartByUsername(user).orElse(cartService.create(user));
        }
        return ResponseEntity.ok(new CartDto(cart));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable String id, Principal principal) {
        Cart cart;
        if (principal == null) {
            cart = cartService.getCartById(id).orElseThrow(() -> new ResourceNotFoundException("Cart: " + id + " not found."));
        } else {
            String user = principal.getName();
            cart = cartService.getCart(id, user).orElseThrow(() -> new BadRequestException("Failed to merge cart:" + id + " for user: " + user));
        }
        return ResponseEntity.ok(new CartDto(cart));
    }

    @GetMapping("/{cid}/add/{pid}")
    public void addItem(@PathVariable(name = "cid") String cartId, @PathVariable(name = "pid") Long productId) {
        cartService.addItem(cartId, productId);
    }

    @GetMapping("/{cid}/remove/{pid}")
    public void removeItem(@PathVariable(name = "cid") String cartId, @PathVariable(name = "pid") Long productId) {
        cartService.removeItem(cartId, productId);
    }

    @GetMapping("/{cid}/delete/{pid}")
    public void deleteItem(@PathVariable(name = "cid") String cartId, @PathVariable(name = "pid") Long productId) {
        cartService.deleteItem(cartId, productId);
    }

    @GetMapping("/{cid}/clear")
    public void clearCart(@PathVariable(name = "cid") String cartId) {
        cartService.clear(cartId);
    }
}


