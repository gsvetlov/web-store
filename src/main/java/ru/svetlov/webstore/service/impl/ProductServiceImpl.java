package ru.svetlov.webstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.repository.ProductRepository;
import ru.svetlov.webstore.service.ProductService;


import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final Validator validator;

    @Override
    public boolean exists(Long id){
        return productRepository.existsById(id);
    }

    @Override
    public List<Product> getAllByIdIn(Collection<Long> ids) {
        return Collections.unmodifiableList(productRepository.findAllByIdIn(ids));
    }

    @Override
    public Optional<Product> getById(Long id) {
        throwIfNotValid(validator.validateValue(Product.class, "id", id));
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> create(String title, Double cost) {
        throwIfNotValid(validator.validateValue(Product.class, "title", title));
        throwIfNotValid(validator.validateValue(Product.class, "cost", BigDecimal.valueOf(cost)));
        return Optional.of(productRepository.save(new Product(title, cost)));
    }

    @Override
    public Optional<Product> update(Product product) {
        throwIfNotValid(validator.validate(product));
        return Optional.of(productRepository.save(product));
    }

    @Override
    @Transactional
    public void update(Long id, String title, Double cost) {
        Product product = productRepository.getById(id);
        product.setTitle(title);
        product.setCost(BigDecimal.valueOf(cost));

        throwIfNotValid(validator.validate(product));
        productRepository.save(product);

    }

    @Override
    public void deleteById(Long id) {
        throwIfNotValid(validator.validateValue(Product.class, "id", id));
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
            return productRepository
                    .findAllByCostLessThanEqual(PageRequest.of(page, pageSize), BigDecimal.valueOf(maxPrice));
        }
        return productRepository.findAllByCostBetween(
                PageRequest.of(page, pageSize),
                BigDecimal.valueOf(minPrice),
                BigDecimal.valueOf(maxPrice));
    }

    private void throwIfNotValid(Set<ConstraintViolation<Product>> violations) {
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations
                    .stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")));
        }
    }
}
