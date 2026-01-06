package com.example.delivery_tracking.controller;

import com.example.delivery_tracking.dto.CreateProductRequest;
import com.example.delivery_tracking.dto.PaginatedResponse;
import com.example.delivery_tracking.entity.Product;
import com.example.delivery_tracking.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public Product createProduct(@RequestBody CreateProductRequest request) {
        return service.createProduct(request);
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable UUID productId) {
        return service.getProductById(productId);
    }

    @GetMapping
    public PaginatedResponse<Product> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return service.findAll(page, size, sortBy, sortDir);
    }

    @DeleteMapping("/{productId}")
    public void deleteProductById(@PathVariable UUID productId) {
        service.deleteProductById(productId);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable UUID productId, @RequestBody CreateProductRequest request) {
        return service.updateProduct(productId, request);
    }
}
