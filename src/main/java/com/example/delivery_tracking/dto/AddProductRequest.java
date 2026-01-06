package com.example.delivery_tracking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter

public class AddProductRequest {
    private UUID productId;
    private Integer amount;
}
