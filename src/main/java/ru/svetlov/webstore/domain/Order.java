package ru.svetlov.webstore.domain;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id")
    private OrderDetails details;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private Collection<OrderItem> orderItems;

    @Column(name = "total")
    private BigDecimal totalSum;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime modified;

    public void setDetails(OrderDetails details) {
        this.details = details;
    }

    public OrderDetails getDetails() {
        return details;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Collection<OrderItem> getOrderItems() {
        return Collections.unmodifiableCollection(orderItems);
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
