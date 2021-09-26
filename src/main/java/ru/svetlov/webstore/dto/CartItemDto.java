package ru.svetlov.webstore.dto;

import lombok.*;
import ru.svetlov.webstore.domain.Product;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;

//@Data
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
public class CartItemDto implements Serializable {
    @Min(value = 1, message = "Invalid id")
    private Long productId;

    private String title;

    @Min(value = 1, message = "Invalid quantity")
    private int quantity;

    private BigDecimal productPrice;

    private BigDecimal itemSum;

    public CartItemDto(Product product) {
        this.productId = product.getId();
        this.productPrice = product.getCost();
        this.quantity = 1;
        this.itemSum = product.getCost();
        this.title = product.getTitle();
    }

    public void changeQuantity(int delta) {
        quantity += delta;
        if (quantity < 0) {
            quantity = 0;
        }
        recalculateSum();
    }

    public void changePrice(BigDecimal value) {
        productPrice = value;
        recalculateSum();
    }

    private void recalculateSum() {
        itemSum = BigDecimal.valueOf(quantity).multiply(productPrice);
    }

}
