package ru.svetlov.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.service.ProductService;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getById(id).orElseThrow();
    }

    @GetMapping("/products")
    public List<Product> getAll(
            @RequestParam(required = false, name = "min_price") Double minPrice,
            @RequestParam(required = false, name = "max_price") Double maxPrice) {
        return productService.getAll(minPrice, maxPrice);
    }

    @PostMapping("/products")
    public String create(@RequestParam(name = "title") String title, @RequestParam(name = "cost") double cost) {
        return productService.create(title, cost).toString();
    }

    @GetMapping("/products/delete/{id}")
    public void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }
}
