package ru.svetlov.webstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Collection<CartItemDto> contents;
    private BigDecimal total;
}
