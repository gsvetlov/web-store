package ru.svetlov.webstore.repository;

import ru.svetlov.webstore.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> getById(Long id);
    List<Product> getAll();
    Product create(String title, double cost);
}
