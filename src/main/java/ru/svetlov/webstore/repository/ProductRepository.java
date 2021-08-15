package ru.svetlov.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
