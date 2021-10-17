package ru.svetlov.webstore.api.dto;

import java.math.BigDecimal;
import java.util.Collection;

public class CartDto {
    private String cartId;
    private Collection<CartItemDto> contents;
    private BigDecimal total;

    public CartDto() {
    }

    public CartDto(String cartId, Collection<CartItemDto> contents, BigDecimal total) {
        this.cartId = cartId;
        this.contents = contents;
        this.total = total;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Collection<CartItemDto> getContents() {
        return contents;
    }

    public void setContents(Collection<CartItemDto> contents) {
        this.contents = contents;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
