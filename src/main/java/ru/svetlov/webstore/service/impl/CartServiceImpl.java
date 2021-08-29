package ru.svetlov.webstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.dto.CartItemDto;
import ru.svetlov.webstore.service.CartService;
import ru.svetlov.webstore.service.ProductService;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ProductService productService;

    private Map<Long, Integer> contents;

    @PostConstruct
    private void init() {
        contents = new HashMap<>();
        contents.put(1L, 10);
        contents.put(3L, 20);
        contents.put(5L, 30);
        contents.put(7L, 40);
    }


    @Override
    public Map<Product, Integer> getAll() {
        Map<Product, Integer> result = new HashMap<>(contents.size());
        Set<Product> products = contents.keySet().stream()
                .map(productService::getById)
                .map(optional -> optional.orElseThrow(IllegalArgumentException::new))
                .collect(Collectors.toSet());
        products.forEach(product -> result.put(product, contents.get(product.getId())));
        return Collections.unmodifiableMap(result);
    }

    @Override
    public void addItem(CartItemDto dto) {
        throwIfProductNotExist(dto.getId());
        contents.put(dto.getId(), dto.getQuantity());
    }

    @Override
    public void updateItem(CartItemDto dto) {
        Long id = dto.getId();
        throwIfIdNotExist(id);
        throwIfProductNotExist(id);
        contents.put(id, dto.getQuantity());
    }

    @Override
    public void removeItem(CartItemDto dto) {
        throwIfProductNotExist(dto.getId());
        contents.remove(dto.getId());
    }

    private void throwIfProductNotExist(Long id) {
        if (productService.exists(id)) {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
    }

    private void throwIfIdNotExist(Long id) {
        if (!contents.containsKey(id))
            throw new IllegalArgumentException("Invalid item id: " + id);
    }
}
