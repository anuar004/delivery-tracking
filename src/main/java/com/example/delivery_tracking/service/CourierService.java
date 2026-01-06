package com.example.delivery_tracking.service;

import com.example.delivery_tracking.dto.CreateCourierRequest;
import com.example.delivery_tracking.dto.PaginatedResponse;
import com.example.delivery_tracking.entity.Courier;
import com.example.delivery_tracking.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor

public class CourierService {

    private final CourierRepository courierRepository;

    public Courier createCourier(CreateCourierRequest request) {
        Courier courier = new Courier();
        courier.setName(request.getName());
        courierRepository.save(courier);
        return courier;
    }

    public Courier getCourier(UUID courierId) {
        return courierRepository.findById(courierId)
                .orElseThrow(() -> new RuntimeException("Courier Not Found"));
    }

    public PaginatedResponse<Courier> findAll(int page, int size, String sortBy, String sortDir) {
        Sort.Order order = "desc".equalsIgnoreCase(sortDir)
                ? Sort.Order.desc(sortBy)
                : Sort.Order.asc(sortBy);

        Sort sort = Sort.by(order);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Courier> couriers = courierRepository.findAll(pageable);
        return new PaginatedResponse<>(
                couriers.getContent(),
                couriers.getNumber(),
                couriers.getSize(),
                couriers.getTotalPages(),
                couriers.getTotalElements()
        );
    }

    public void deleteCourierById(UUID courierId) {
        courierRepository.deleteById(courierId);
    }

    public Courier updateCourier(UUID id, CreateCourierRequest request) {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courier Not Found"));
        if(request.getName() != null) courier.setName(request.getName());
        courierRepository.save(courier);
        return courier;
    }

}
