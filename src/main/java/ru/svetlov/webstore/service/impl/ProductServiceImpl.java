package ru.svetlov.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.repository.ProductRepository;
import ru.svetlov.webstore.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product getById(Long id) {
        return repository.getById(id).orElse(Product.Null());
    }

    @Override
    public List<Product> getAll() {
        return repository.getAll();
    }
}
