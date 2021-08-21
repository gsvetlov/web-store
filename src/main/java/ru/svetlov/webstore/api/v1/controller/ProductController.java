package ru.svetlov.webstore.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.dto.ProductDto;
import ru.svetlov.webstore.exception.BadRequestException;
import ru.svetlov.webstore.exception.ResourceNotFoundException;
import ru.svetlov.webstore.service.ProductService;

import java.math.BigDecimal;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return new ProductDto(productService.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item.id=" + id + " not found")));
    }

    @GetMapping
    public Page<ProductDto> getAll(
            @RequestParam(required = false, name = "min_price") Double minPrice,
            @RequestParam(required = false, name = "max_price") Double maxPrice,
            @RequestParam(required = false, name = "p", defaultValue = "0") int page,
            @RequestParam(required = false, name = "ps", defaultValue = "10") int pageSize) {
        return productService.getAll(page, pageSize, minPrice, maxPrice).map(ProductDto::new);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) {
        return new ResponseEntity<>(new ProductDto(createProduct(dto)),
                HttpStatus.CREATED);
    }

    private Product createProduct(ProductDto dto) {
        return productService.create(dto.getTitle(), dto.getPrice())
                .orElseThrow(() -> new BadRequestException("Failed to create product: " + dto));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PutMapping
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto dto) {
        Optional<Product> optional = productService.getById(dto.getId());
        Product result = optional.isPresent() ? updateProduct(dto, optional.get()) : createProduct(dto);
        return new ResponseEntity<>(new ProductDto(result), HttpStatus.OK);
    }

    private Product updateProduct(ProductDto dto, Product product){
        product.setTitle(dto.getTitle());
        product.setCost(BigDecimal.valueOf(dto.getPrice()));
        return productService.update(product).orElseThrow(() ->
                new BadRequestException("Failed to update product: " + product + " with " + dto));
    }

}
