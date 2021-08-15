package ru.svetlov.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.repository.ProductRepository;
import ru.svetlov.webstore.service.ProductService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAll() {
        return Collections.unmodifiableList(productRepository.findAll());
    }

    @Override
    public Product create(String title, Double cost) {
        return productRepository.save(new Product(title, cost));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAll(Double minPrice, Double maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return productRepository.findAll();
        }
        if (maxPrice == null) {
            return productRepository.findAllByCostGreaterThanEqual(BigDecimal.valueOf(minPrice));
        }
        if (minPrice == null) {
            return productRepository.findAllByCostLessThanEqual(BigDecimal.valueOf(maxPrice));
        }
        return productRepository.findAllByCostBetween(BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice));
    }
}
