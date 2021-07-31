package ru.svetlov.webstore.repository.impl;

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

    public InMemoryProductRepository() {
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

    @Override
    public Product create(String title, double cost) {
        long nextId = products.stream().mapToLong(Product::getId).max().orElse(0L) + 1;
        Product product = new Product(nextId, title, cost);
        products.add(product);
        return product;
    }


    @Override
    public void update(Product product) {
        // ничего не делаем, так как состояние объекта уже изменено сервисом, но для работы с БД обновлять придется.
/*
        Product entry = products.stream()
                .filter(e -> e.getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if (entry == null) return;
        products.set(products.indexOf(entry), product);
*/
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
