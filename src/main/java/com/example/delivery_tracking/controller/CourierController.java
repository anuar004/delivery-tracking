package com.example.delivery_tracking.controller;

import com.example.delivery_tracking.dto.CreateCourierRequest;
import com.example.delivery_tracking.dto.PaginatedResponse;
import com.example.delivery_tracking.entity.Courier;
import com.example.delivery_tracking.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/couriers")
@RequiredArgsConstructor

public class CourierController {
    private final CourierService courierService;

    @PostMapping
    public Courier createCourier(@RequestBody CreateCourierRequest request) {
        return courierService.createCourier(request);
    }

    @GetMapping("/{courierId}")
    public Courier getCourierById(@PathVariable UUID courierId) {
        return courierService.getCourier(courierId);
    }

    @GetMapping
    public PaginatedResponse<Courier> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return courierService.findAll(page, size, sortBy, sortDir);
    }

    @DeleteMapping("/{courierId}")
    public void deleteCourierById(@PathVariable UUID courierId) {
        courierService.deleteCourierById(courierId);
    }

    @PutMapping("/{courierId}")
    public Courier updateCourier(@PathVariable UUID courierId, @RequestBody CreateCourierRequest request) {
        return courierService.updateCourier(courierId, request);
    }
}

