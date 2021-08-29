package ru.svetlov.webstore.domain;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Table(name = "products")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Min(value = 1, message = "Invalid id")
    private Long id;

    @Column(name = "title", length = 127, nullable = false)
    @Length(min = 3, max = 127, message = "Title length is between 3-127 symbols")
    private String title;

    @Column(name = "cost", scale = 12, precision = 2, nullable = false)
    @DecimalMin(value = "0.01", message = "Cost less than 0.01 is not allowed")
    private BigDecimal cost;

    public Product(String title, BigDecimal cost) {
        this.title = title;
        this.cost = cost;
    }

    public Product(String title, Double cost) {
        this.title = title;
        this.cost = BigDecimal.valueOf(cost);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                '}';
    }

    protected Product() {
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
