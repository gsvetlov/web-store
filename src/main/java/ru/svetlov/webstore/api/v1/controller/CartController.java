package ru.svetlov.webstore.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.dto.CartDto;
import ru.svetlov.webstore.dto.CartItemDto;
import ru.svetlov.webstore.service.CartService;
import ru.svetlov.webstore.util.ControllerUtil;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDto> getCartContents() {
        return new ResponseEntity<>(new CartDto(cartService.getAll()), HttpStatus.OK);
    }

    @PostMapping
    public void addItem(@Validated @RequestBody CartItemDto dto, BindingResult bindingResult) {
        ControllerUtil.throwIfNotValid(bindingResult);
        cartService.addItem(dto);
    }

    @PutMapping
    public void changeItem(@Validated @RequestBody CartItemDto dto, BindingResult bindingResult) {
        ControllerUtil.throwIfNotValid(bindingResult);
        cartService.updateItem(dto);
    }

    @DeleteMapping
    public void removeItem(@RequestBody CartItemDto dto) {
        cartService.removeItem(dto);
    }
}


