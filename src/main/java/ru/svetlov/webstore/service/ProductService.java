package ru.svetlov.webstore.service;

import ru.svetlov.webstore.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getById(Long id);
    List<Product> getAll();
    Product create(String title, Double cost);
    void deleteById(Long id);

    List<Product> getAll(Double minPrice, Double maxPrice);
}
