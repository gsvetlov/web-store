package ru.svetlov.webstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.dto.CartItemDto;
import ru.svetlov.webstore.service.CartService;
import ru.svetlov.webstore.service.ProductService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

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
    public Map<Long, Integer> getAll() {
        return Collections.unmodifiableMap(contents);
    }

    @Override
    public void addItem(CartItemDto dto) {
        throwIfProductNotExist(dto.getId());
        contents.put(dto.getId(), dto.getQuantity());
    }

    @Override
    public void updateItem(CartItemDto dto) {
        throwIfIdNotExist(dto.getId());
        throwIfProductNotExist(dto.getId());
        contents.put(dto.getId(), dto.getQuantity());
    }

    @Override
    public BigDecimal getTotalSum() {
        return productService.getAllByIdIn(contents.keySet()).stream()
                .map(p -> p.getCost().multiply(BigDecimal.valueOf(contents.get(p.getId()))))
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    @Override
    public void removeItem(CartItemDto dto) {
        throwIfProductNotExist(dto.getId());
        contents.remove(dto.getId());
    }

    private void throwIfProductNotExist(Long id) {
        if (!productService.exists(id)) {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
    }

    private void throwIfIdNotExist(Long id) {
        if (!contents.containsKey(id))
            throw new IllegalArgumentException("Invalid cart item id: " + id);
    }
}
