package com.example.delivery_tracking.dto;

import com.example.delivery_tracking.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateOrderStatusRequest {
    private OrderStatus status;
}
