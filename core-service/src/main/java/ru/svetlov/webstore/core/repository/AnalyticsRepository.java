package ru.svetlov.webstore.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.core.domain.OrderItem;

@Repository
public interface AnalyticsRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "SELECT count(ord) FROM orders ord LEFT JOIN order_items it ON ord.order_id = it.order_id WHERE ord.user_id = :uid AND it.product_id = :pid", nativeQuery = true)
    int getOrderItemQuantity(@Param(value = "uid") Long userId, @Param(value = "pid") Long productId);
}
