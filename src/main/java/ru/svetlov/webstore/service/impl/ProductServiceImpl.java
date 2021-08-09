package ru.svetlov.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.repository.ProductRepository;
import ru.svetlov.webstore.service.ProductService;

import java.util.List;
import java.util.Optional;

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

    @Override
    public boolean create(String title, double cost) {
        if (cost < 0) return false;
        return repository.create(title, cost).getId() > 0;
    }

    @Override
    public void changeCost(Long id, double value) {
        Product product = checkExist(id);
        changeIfCostIsPositive(product, value);
    }

    private void changeIfCostIsPositive(Product product, double value) {
        if (product.equals(Product.Null())) return;
        double newCost = product.getCost() + value;
        if (newCost < 0) return;
        product.setCost(newCost);
        repository.update(product);
    }

    private Product checkExist(Long id) {
        return getById(id);
    }
}
