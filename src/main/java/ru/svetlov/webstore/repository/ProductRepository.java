package ru.svetlov.webstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.domain.Product;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsById(Long id);
    Page<Product> findAllByCostBetween(Pageable request, BigDecimal minPrice, BigDecimal maxPrice);
    Page<Product> findAllByCostGreaterThanEqual(Pageable request, BigDecimal minPrice);
    Page<Product> findAllByCostLessThanEqual(Pageable request, BigDecimal maxPrice);
}
