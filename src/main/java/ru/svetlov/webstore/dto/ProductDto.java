package ru.svetlov.webstore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.svetlov.webstore.domain.Product;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProductDto {

    @Min(value = 1, message = "Invalid id")
    private Long id;

    @NotNull(message = "title can't be empty")
    @Length(min = 3, max = 127, message = "Title length should be between 3-127 symbols")
    private String title;

    @NotNull(message = "price can't be empty")
    @DecimalMin(value = "0.01", message = "Price less than 0.01 is not allowed")
    private Double price;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getCost().doubleValue();
    }
}
