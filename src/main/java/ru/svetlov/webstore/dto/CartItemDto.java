package ru.svetlov.webstore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class CartItemDto {
    @Min(value = 1, message = "Invalid id")
    private long id;

    @Min(value = 1, message = "Invalid quantity")
    private int quantity;
}
