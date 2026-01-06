package com.example.delivery_tracking.service;

import com.example.delivery_tracking.dto.*;
import com.example.delivery_tracking.entity.Courier;
import com.example.delivery_tracking.entity.Order;
import com.example.delivery_tracking.entity.OrderProduct;
import com.example.delivery_tracking.enums.OrderStatus;
import com.example.delivery_tracking.repository.OrderProductRepository;
import com.example.delivery_tracking.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CourierService courierService;
    private final OrderHistoryService orderHistoryService;
    private final OrderProductService  orderProductService;
    private final OrderProductRepository orderProductRepository;


    public Order create(CreateOrderRequest request) {
        Order order = new Order();
        order.setLocation(request.getLocation());
        order.setStatus(OrderStatus.CREATED);
        order.setProducts(new ArrayList<>());
        orderRepository.save(order);

        return order;
    }


    public Order assignCourier(UUID orderId, UUID courierId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        Courier courier = this.courierService.getCourier(courierId);

        order.setCourier(courier);
        order.setStatus(OrderStatus.ASSIGNED);

        orderRepository.save(order);

        return order;
    }

    public Order updateStatus(UUID orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        order.setStatus(request.getStatus());
        orderRepository.save(order);

        this.orderHistoryService.create(order, request.getStatus());

        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
    }

    public Order addProduct (UUID orderId, AddProductRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        this.orderProductService.createOrderProduct(order, request);
        orderRepository.save(order);
        return order;
    }

    public PaginatedResponse<Order> findAll(int page, int size, String sortBy, String sortDir) {
        Sort.Order order = "desc".equalsIgnoreCase(sortDir)
                ? Sort.Order.desc(sortBy)
                : Sort.Order.asc(sortBy);

        Sort sort = Sort.by(order);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Order> orders = orderRepository.findAll(pageable);
        return new PaginatedResponse<>(
                orders.getContent(),
                orders.getNumber(),
                orders.getSize(),
                orders.getTotalPages(),
                orders.getTotalElements()
        );
    }

        public void deleteOrderById(UUID orderId) {
            orderRepository.deleteById(orderId);
    }

    public void updateTotalPrice(OrderProduct orderProduct) {
        Order order = orderProduct.getOrder();
        order.setTotalPrice(calculateTotalPrice(order));
        orderRepository.save(order);
    }

    private BigDecimal calculateTotalPrice(Order order) {
        return order.getProducts().stream()
                .map(op ->
                        op.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(op.getAmount()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
