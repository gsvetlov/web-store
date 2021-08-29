package ru.svetlov.webstore.service;

import org.springframework.data.domain.Page;
import ru.svetlov.webstore.domain.Product;

import java.util.Optional;

public interface ProductService {
    boolean exists(Long id);
    Optional<Product> getById(Long id);
    Optional<Product> create(String title, Double cost);
    Optional<Product> update(Product product);
    void update(Long id, String title, Double cost);
    void deleteById(Long id);
    Page<Product> getAll(int page, int itemsPerPage, Double minPrice, Double maxPrice);
}
