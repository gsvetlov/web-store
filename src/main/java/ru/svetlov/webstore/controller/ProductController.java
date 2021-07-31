package ru.svetlov.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String getProductById(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getById(id));
        return "product_info";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model){
        model.addAttribute("products", productService.getAll());
        return "products";
    }

    @GetMapping("/product/create")
    public String showCreateProductForm(){
        return "create_product";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("title") String title, @RequestParam("cost") double cost){
        boolean result = productService.create(title, cost);
        if (result) return "redirect:/products";
        return "create_fail";
    }
}
