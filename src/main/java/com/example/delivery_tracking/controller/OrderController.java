package com.example.delivery_tracking.controller;

import com.example.delivery_tracking.dto.*;
import com.example.delivery_tracking.entity.Order;
import com.example.delivery_tracking.entity.OrderHistory;
import com.example.delivery_tracking.service.OrderHistoryService;
import com.example.delivery_tracking.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;
    private final OrderHistoryService orderHistoryService;

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        return service.create(request);
    }

    @PutMapping("/{orderId}/assign")
    public Order assignCourier(
            @PathVariable UUID orderId,
            @RequestBody AssignCourierRequest request) {
        return service.assignCourier(orderId, request.getCourierId());
    }

    @PatchMapping("/{orderId}/status")
    public Order updateStatus(
            @PathVariable UUID orderId,
            @RequestBody UpdateOrderStatusRequest request) {
        return service.updateStatus(orderId, request);
    }

    @GetMapping("/{orderId}/history")
    public List<OrderHistory> getHistory(@PathVariable UUID orderId) {
        return orderHistoryService.getHistory(orderId);
    }

    @PutMapping("/{orderId}/products")
    public Order addProduct(
            @PathVariable UUID orderId,
            @RequestBody AddProductRequest request) {
        return service.addProduct(orderId, request);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable UUID orderId) {
        return service.getOrderById(orderId);
    }

    @GetMapping
    public PaginatedResponse<Order> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "location") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return service.findAll(page, size, sortBy, sortDir);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrderById(@PathVariable UUID orderId) {
        service.deleteOrderById(orderId);
    }

}
