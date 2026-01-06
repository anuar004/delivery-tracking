package com.example.delivery_tracking.service;

import com.example.delivery_tracking.entity.Order;
import com.example.delivery_tracking.entity.OrderHistory;
import com.example.delivery_tracking.enums.OrderStatus;
import com.example.delivery_tracking.repository.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class OrderHistoryService {
    private final OrderHistoryRepository orderHistoryRepository;

    public void create(Order order, OrderStatus status) {
            OrderHistory history = new OrderHistory();
            history.setOrderId(order.getId());
            history.setStatus(status);
            history.setLocation(order.getLocation());
            orderHistoryRepository.save(history);
        }

    public List<OrderHistory> getHistory(UUID orderId) {
        return orderHistoryRepository
                .findByOrderIdOrderByTimestampAsc(orderId);
    }
}
