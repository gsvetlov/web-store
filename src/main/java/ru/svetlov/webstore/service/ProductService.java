package ru.svetlov.webstore.service;

import ru.svetlov.webstore.domain.Product;

import java.util.List;

public interface ProductService {
    Product getById(Long id);
    List<Product> getAll();
    boolean create(String title, double cost);
    void changeCost(Long id, double value);
}
