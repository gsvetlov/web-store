package ru.svetlov.webstore.repository.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.repository.ProductRepository;

import java.util.*;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products;

    public InMemoryProductRepository(List<Product> products) {
        this.products = products;
    }

    public InMemoryProductRepository(){
        products = initialize();
    }

    @Override
    public Optional<Product> getById(Long id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Collections.unmodifiableList(products);
    }

    private List<Product> initialize() {
        return new ArrayList<>(Arrays.asList(
                new Product(1L, "Bread", 1.50),
                new Product(2L, "Butter", 2.65),
                new Product(3L, "Milk", 1.12),
                new Product(4L, "Cheese", 5.15),
                new Product(5L, "Carrots", 0.45)
        ));
    }
}
