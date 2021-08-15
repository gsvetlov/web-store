package ru.svetlov.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.service.ProductService;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.getById(id);
    }

    @GetMapping("/products")
    public List<Product> getAll(){
        return productService.getAll();
    }
}
