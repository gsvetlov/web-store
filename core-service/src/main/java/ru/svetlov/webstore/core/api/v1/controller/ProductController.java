package ru.svetlov.webstore.core.api.v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.svetlov.webstore.api.dto.CommentDto;
import ru.svetlov.webstore.api.dto.CommentPageDto;
import ru.svetlov.webstore.api.dto.ProductDto;
import ru.svetlov.webstore.core.domain.Comment;
import ru.svetlov.webstore.core.domain.Product;
import ru.svetlov.webstore.core.exception.BadRequestException;
import ru.svetlov.webstore.core.exception.ResourceNotFoundException;
import ru.svetlov.webstore.core.service.CommentService;
import ru.svetlov.webstore.core.service.ProductService;
import ru.svetlov.webstore.core.util.ControllerUtil;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        Product product = productService.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item.id=" + id + " not found"));
        return new ProductDto(product.getId(), product.getTitle(), product.getCost().doubleValue());
    }

    @GetMapping
    public Page<ProductDto> getAll(
            @RequestParam(required = false, name = "min_price") Double minPrice,
            @RequestParam(required = false, name = "max_price") Double maxPrice,
            @RequestParam(required = false, name = "p", defaultValue = "0") int page,
            @RequestParam(required = false, name = "ps", defaultValue = "10") int pageSize) {
        return productService.getAll(page, pageSize, minPrice, maxPrice).map(product -> new ProductDto(product.getId(), product.getTitle(), product.getCost().doubleValue()));
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@Validated @RequestBody ProductDto dto, BindingResult bindingResult) {
        ControllerUtil.throwIfNotValid(bindingResult);
        Product product = createProduct(dto);
        return new ResponseEntity<>(new ProductDto(product.getId(), product.getTitle(), product.getCost().doubleValue()),
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
    public ResponseEntity<?> getComments(Principal principal, @PathVariable(name = "pid") Long pid) {
        List<CommentDto> comments = commentService
                .getAllByProduct(pid)
                .stream()
                .map(comment -> new CommentDto(comment.getContent(), comment.getUser().getUsername(), comment.getModified()))
                .collect(Collectors.toList());
        boolean isUserEligibleToComment = principal != null && commentService.canLeaveComment(principal.getName(), pid);
        return new ResponseEntity<>(new CommentPageDto(comments, isUserEligibleToComment), HttpStatus.OK);
    }

    @PostMapping("/{id}/comments/add")
    public ResponseEntity<?> addComment(Principal principal,
                                        @PathVariable(name = "id") Long productId,
                                        @RequestBody String content) {
        Comment comment = commentService.add(principal.getName(), productId, content);
        CommentDto dto = new CommentDto(comment.getContent(), comment.getUser().getUsername(), comment.getModified());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
