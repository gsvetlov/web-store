package ru.svetlov.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.repository.ProductRepository;
import ru.svetlov.webstore.service.ProductService;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getAll() {
        return Collections.unmodifiableList(productRepository.findAll());
    }
}
