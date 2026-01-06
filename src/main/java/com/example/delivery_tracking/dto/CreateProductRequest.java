package com.example.delivery_tracking.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class CreateProductRequest {
    private String title;
    private String description;
    private BigDecimal price;
}
