package ru.svetlov.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.service.ProductService;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    @ResponseBody
    public String getProductById(@PathVariable Long id){
        return productService.getById(id).toString();
    }

    @GetMapping("/product")
    @ResponseBody
    public List<Product> getAllProducts(){
        return productService.getAll();
    }
}
