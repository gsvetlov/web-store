package ru.svetlov.webapp.order.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)

@NamedEntityGraph(name = "order-with-items", attributeNodes = @NamedAttributeNode(value = "orderItems"))

@Entity
@Table(name = "orders", indexes = @Index(name = "idx_order_user_id", columnList = "user_id, order_id"))
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private OrderDetails details;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Collection<OrderItem> orderItems;

    @Column(name = "total")
    private BigDecimal totalSum;

    @Basic
    @Column(name = "status_code")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int statusCode;

    @Transient
    @Setter(AccessLevel.PUBLIC)
    private OrderStatus status;

    @PostLoad
    private void loadStatus() {
        if (statusCode > 0) {
            status = OrderStatus.of(statusCode);
        }
    }

    @PrePersist
    private void saveStatus() {
        if (status != null) {
            statusCode = status.getStatus();
        }
    }

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime modified;

    public Order(Long userId) {
        this.userId = userId;
        status = OrderStatus.NEW;
    }

    public void setDetails(OrderDetails details) {
        details.setOrder(this);
        this.details = details;
    }

    public OrderDetails getDetails() {
        return details;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        orderItems.forEach(oi -> oi.setOrder(this));
        this.orderItems = orderItems;
        totalSum = orderItems.stream().map(OrderItem::getItemPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Collection<OrderItem> getOrderItems() {
        return Collections.unmodifiableCollection(orderItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order other = (Order) o;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
