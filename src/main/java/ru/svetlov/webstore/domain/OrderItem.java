package ru.svetlov.webstore.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "item_price")
    private BigDecimal itemPrice;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime modified;

    public static OrderItem of(Product product, int quantity, BigDecimal productPrice, BigDecimal itemPrice) {
        return new OrderItem(product, quantity, productPrice, itemPrice);
    }

    protected OrderItem(Product product, int quantity, BigDecimal productPrice, BigDecimal itemPrice) {
        this.product = product;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.itemPrice = itemPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem other = (OrderItem) o;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}