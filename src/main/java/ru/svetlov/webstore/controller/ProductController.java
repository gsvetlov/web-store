package ru.svetlov.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.dto.ProductDto;
import ru.svetlov.webstore.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return new ProductDto(productService.getById(id).orElseThrow());
    }

    @GetMapping("/products")
    public List<ProductDto> getAll(
            @RequestParam(required = false, name = "min_price") Double minPrice,
            @RequestParam(required = false, name = "max_price") Double maxPrice) {
        return productService
                .getAll(minPrice, maxPrice)
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/products")
    public ProductDto create(@RequestParam(name = "title") String title, @RequestParam(name = "cost") double cost) {
        return new ProductDto(productService.create(title, cost));
    }

    @GetMapping("/products/delete/{id}")
    public void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }
}
