package ru.svetlov.webstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.svetlov.webstore.util.cart.Cart;

import java.math.BigDecimal;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private String cartId;
    private Collection<CartItemDto> contents;
    private BigDecimal total;

    public CartDto(Cart cart){
        this(cart.getId(), cart.getContents(), cart.getTotal());
    }
}
