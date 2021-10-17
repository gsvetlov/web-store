package ru.svetlov.webstore.core.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Table(name = "order_details")
@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "details_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "details")
    private Order order;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime modified;

    public static OrderDetails of(String address, String phone) {
        return new OrderDetails(address, phone);
    }

    protected OrderDetails(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetails other = (OrderDetails) o;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}