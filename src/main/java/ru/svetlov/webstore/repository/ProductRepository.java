package ru.svetlov.webstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.domain.Product;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsById(@NonNull Long id);
    List<Product> findAllByIdIn(Collection<@Min(value = 1, message = "Invalid id") Long> id);
    Page<Product> findAllByCostBetween(Pageable request, BigDecimal minPrice, BigDecimal maxPrice);
    Page<Product> findAllByCostGreaterThanEqual(Pageable request, BigDecimal minPrice);
    Page<Product> findAllByCostLessThanEqual(Pageable request, BigDecimal maxPrice);
}
