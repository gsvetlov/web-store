package ru.svetlov.webstore.dto;

import lombok.Data;
import ru.svetlov.webstore.domain.Product;

@Data
public class ProductDto {
    private Long id;
    private String title;
    private double price;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getCost().doubleValue();
    }
}
