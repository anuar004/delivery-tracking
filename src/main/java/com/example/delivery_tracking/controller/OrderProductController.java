package com.example.delivery_tracking.controller;

import com.example.delivery_tracking.dto.UpdateAmountRequest;
import com.example.delivery_tracking.entity.OrderProduct;
import com.example.delivery_tracking.service.OrderProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/order-products")
@RequiredArgsConstructor

public class OrderProductController {

    private final OrderProductService service;

    @DeleteMapping("/{orderId}")
    public void deleteOrderProductById(@PathVariable UUID orderId) {
        service.deleteOrderProductById(orderId);
    }

    @PutMapping("/{orderProductId}")
    public OrderProduct updateProductAmount(@PathVariable UUID orderProductId, @RequestBody UpdateAmountRequest request) {
        return service.updateProductAmount(orderProductId, request);
    }
}
