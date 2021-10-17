package ru.svetlov.webstore.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItemDto implements Serializable {
    private Long productId;

    private String title;

    private int quantity;

    private BigDecimal productPrice;

    private BigDecimal itemSum;

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

    public CartItemDto() {
    }

    public CartItemDto(Long productId, String title, int quantity, BigDecimal productPrice, BigDecimal itemSum) {
        this.productId = productId;
        this.title = title;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.itemSum = itemSum;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getItemSum() {
        return itemSum;
    }

    public void setItemSum(BigDecimal itemSum) {
        this.itemSum = itemSum;
    }
}
