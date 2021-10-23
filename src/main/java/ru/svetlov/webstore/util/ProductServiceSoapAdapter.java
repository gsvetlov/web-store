package ru.svetlov.webstore.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.svetlov.webstore.dto.soap.Product;
import ru.svetlov.webstore.exception.ResourceNotFoundException;
import ru.svetlov.webstore.service.ProductService;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductServiceSoapAdapter {
    private final ProductService productService;

    public Product getById(Long id) {
        return productService.getById(id).map(toSoap).orElseThrow(()-> new ResourceNotFoundException("user id: " + id +" not found"));
    }

    public Collection<Product> getAll() {
        return productService.getAll().stream().map(toSoap).collect(Collectors.toList());
    }

    private static Function<ru.svetlov.webstore.domain.Product, Product> toSoap  = p -> {
        Product product = new Product();
        product.setId(p.getId());
        product.setTitle(p.getTitle());
        product.setPrice(p.getCost());
        return product;
    };
}
