package ru.svetlov.webstore.core.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.svetlov.webstore.api.dtos.CartDto;
import ru.svetlov.webstore.api.exceptions.BadRequestException;
import ru.svetlov.webstore.api.exceptions.ResourceNotFoundException;
import ru.svetlov.webstore.core.service.CartService;
import ru.svetlov.webstore.core.util.cart.Cart;

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
        return ResponseEntity.ok(new CartDto(cart.getId(), cart.getContents(), cart.getTotal()));
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
        return ResponseEntity.ok(new CartDto(cart.getId(), cart.getContents(), cart.getTotal()));
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


