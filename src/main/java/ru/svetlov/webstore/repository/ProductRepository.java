package ru.svetlov.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.domain.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCostGreaterThanEqual(BigDecimal minPrice);
    List<Product> findAllByCostLessThanEqual(BigDecimal maxPrice);
    List<Product> findAllByCostBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
