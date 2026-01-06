package com.example.delivery_tracking.service;

import com.example.delivery_tracking.dto.AddProductRequest;
import com.example.delivery_tracking.dto.UpdateAmountRequest;
import com.example.delivery_tracking.entity.Order;
import com.example.delivery_tracking.entity.OrderProduct;
import com.example.delivery_tracking.entity.Product;
import com.example.delivery_tracking.repository.OrderProductRepository;
import com.example.delivery_tracking.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    public void createOrderProduct(Order order, AddProductRequest request) {
        Product product = productService.getProductById(request.getProductId());

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setAmount(request.getAmount());
        orderProduct.setOrder(order);

        order.getProducts().add(orderProduct);
        orderProductRepository.save(orderProduct);

        order.setTotalPrice(calculateTotalPrice(order));
    }

    public OrderProduct updateProductAmount(UUID orderProductId, UpdateAmountRequest request) {

        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new RuntimeException("Order Product Not Found"));

        if (request.getAmount() != null) {
            orderProduct.setAmount(request.getAmount());
        }

        orderProductRepository.save(orderProduct);

        Order order = orderProduct.getOrder();
        order.setTotalPrice(calculateTotalPrice(order));
        orderRepository.save(order);

        return orderProduct;
    }



    public void deleteOrderProductById(UUID orderProductId) {
        orderProductRepository.deleteById(orderProductId);
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
