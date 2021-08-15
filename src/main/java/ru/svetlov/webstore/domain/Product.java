package ru.svetlov.webstore.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 127, nullable = false)
    private String title;

    @Column(name = "cost", scale = 12, precision = 2, nullable = false)
    private BigDecimal cost;


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

    protected Product() {
    }

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
}
