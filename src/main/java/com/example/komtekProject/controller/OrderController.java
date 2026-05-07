package com.example.komtekProject.controller;

import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.dto.OrderSearchDto;
import com.example.komtekProject.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto request) {
        log.info("Создание заявки для пациента ID: {}", request.getPatientId());
        OrderResponseDto order = orderService.createOrder(request);
        log.info("Заявка создана: ID={}", order.getId());
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        log.info("Получение заявки по ID: {}", id);
        OrderResponseDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search")
    @Transactional(readOnly = true)
    public ResponseEntity<List<OrderResponseDto>> searchOrders(@Valid @ModelAttribute OrderSearchDto searchDto) {
        log.info("Поиск заявок с параметрами: {}", searchDto);
        List<OrderResponseDto> orders = orderService.search(searchDto);
        log.info("Найдено заявок: {}", orders.size());
        return ResponseEntity.ok(orders);
    }
}
