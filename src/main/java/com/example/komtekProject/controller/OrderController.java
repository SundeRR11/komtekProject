package com.example.komtekProject.controller;

import java.time.LocalDate;

import com.example.komtekProject.annotation.ValidDate;
import com.example.komtekProject.annotation.ValidEnp;
import com.example.komtekProject.annotation.ValidSnils;
import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.dto.OrderSearchDto;
import com.example.komtekProject.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto request) {
        OrderResponseDto order = orderService.createOrder(request);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search")
    @Transactional(readOnly = true)
    public ResponseEntity<List<OrderResponseDto>> searchOrders(@Valid @ModelAttribute OrderSearchDto searchDto) {
        return ResponseEntity.ok(orderService.search(searchDto));
    }
}
