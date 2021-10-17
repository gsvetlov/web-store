package ru.svetlov.webstore.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.domain.Comment;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.domain.User;
import ru.svetlov.webstore.dto.CommentDto;
import ru.svetlov.webstore.dto.ProductDto;
import ru.svetlov.webstore.exception.BadRequestException;
import ru.svetlov.webstore.exception.ResourceNotFoundException;
import ru.svetlov.webstore.service.CommentService;
import ru.svetlov.webstore.service.ProductService;
import ru.svetlov.webstore.service.UserService;
import ru.svetlov.webstore.util.ControllerUtil;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final UserService userService;
    private final ProductService productService;
    private final CommentService commentService;

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
    public ResponseEntity<ProductDto> create(@Validated @RequestBody ProductDto dto, BindingResult bindingResult) {
        ControllerUtil.throwIfNotValid(bindingResult);
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
    public void update(@Validated @RequestBody ProductDto dto, BindingResult bindingResult) {
        ControllerUtil.throwIfNotValid(bindingResult);
        if (!productService.exists(dto.getId()))
            throw new ResourceNotFoundException("Product not found: " + dto);
        productService.update(dto.getId(), dto.getTitle(), dto.getPrice());
    }

    @GetMapping("/{pid}/comments")
    public ResponseEntity<Collection<CommentDto>> getComments(@PathVariable(name = "pid") Long pid) {
        List<CommentDto> comments = commentService
                .getByProduct(pid)
                .stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addComment(@PathVariable(name = "id") Long productId, @RequestBody CommentDto dto, Principal principal) {
        User user = userService.getUserByName(principal.getName());
        Product product = productService.getById(productId).orElseThrow(() -> new ResourceNotFoundException("Invalid product id: " + productId));
        Comment comment = commentService.add(user, product, dto.getContent());
        return new ResponseEntity<>(new CommentDto(comment), HttpStatus.CREATED);
    }
}
