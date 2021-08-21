package ru.svetlov.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Product create(String title, Double cost) {
        return productRepository.save(new Product(title, cost));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getAll(int page, int pageSize, Double minPrice, Double maxPrice) {

        if (minPrice == null && maxPrice == null) {
            return productRepository.findAll(PageRequest.of(page, pageSize));
        }
        if (maxPrice == null) {
            return productRepository
                    .findAllByCostGreaterThanEqual(PageRequest.of(page, pageSize), BigDecimal.valueOf(minPrice));
        }
        if (minPrice == null) {
            return productRepository.findAllByCostLessThanEqual(PageRequest.of(page, pageSize), BigDecimal.valueOf(maxPrice));
        }
        return productRepository.findAllByCostBetween(
                PageRequest.of(page, pageSize),
                BigDecimal.valueOf(minPrice),
                BigDecimal.valueOf(maxPrice));
    }
}
