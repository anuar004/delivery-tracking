package com.example.delivery_tracking.service;

import com.example.delivery_tracking.dto.CreateProductRequest;
import com.example.delivery_tracking.dto.PaginatedResponse;
import com.example.delivery_tracking.entity.Product;
import com.example.delivery_tracking.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        productRepository.save(product);
        return product;
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    public PaginatedResponse<Product> findAll(int page, int size, String sortBy, String sortDir) {
        Sort.Order order = "desc".equalsIgnoreCase(sortDir)
                ? Sort.Order.desc(sortBy)
                : Sort.Order.asc(sortBy);

        Sort sort = Sort.by(order);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productRepository.findAll(pageable);
        return new PaginatedResponse<>(
                products.getContent(),
                products.getNumber(),
                products.getSize(),
                products.getTotalPages(),
                products.getTotalElements()
        );
    }

    public void deleteProductById(UUID productId) {
        productRepository.deleteById(productId);
    }

    public Product updateProduct(UUID id, CreateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
        if(request.getTitle() != null) {
            product.setTitle(request.getTitle());
        }

        if(request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        if(request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        productRepository.save(product);
        return product;
    }
}
