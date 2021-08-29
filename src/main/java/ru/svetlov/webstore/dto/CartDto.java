package ru.svetlov.webstore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.svetlov.webstore.domain.Product;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CartDto {
    private Map<ProductDto, Integer> contents;
    private BigDecimal sum;

    public CartDto(Map<Product, Integer> contents) {
        this.contents = new HashMap<>(contents.size());
        contents.forEach((key, value) -> this.contents.put(new ProductDto(key), value));
        sum = this.contents.entrySet().stream()
                .map(e -> BigDecimal.valueOf(e.getKey().getPrice()).multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal::add).orElseThrow();
    }
}
