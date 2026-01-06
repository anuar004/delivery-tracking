package com.example.delivery_tracking.repository;

import com.example.delivery_tracking.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, UUID> {

    List<OrderHistory> findByOrderIdOrderByTimestampAsc(UUID orderId);
}
