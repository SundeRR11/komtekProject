package com.example.komtekProject.controller;


import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto request) {
        OrderResponseDto order = orderService.createOrder(request);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search/by-id/{id}")
    public ResponseEntity<OrderResponseDto> searchById(@PathVariable Long id) {
        OrderResponseDto order = orderService.searchById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search/by-fullname-and-birthdate")
    public ResponseEntity<List<OrderResponseDto>> searchByFullNameAndBirthDate(
            @RequestParam String fullName,
            @RequestParam String birthDate) {
        List<OrderResponseDto> orders = orderService.searchByFullNameAndBirthDate(fullName, birthDate);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/search/by-snils/{snils}")
    public ResponseEntity<List<OrderResponseDto>> searchBySnils(@PathVariable String snils) {
        List<OrderResponseDto> orders = orderService.searchBySnils(snils);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/search/by-enp/{enp}")
    public ResponseEntity<List<OrderResponseDto>> searchByEnp(@PathVariable String enp) {
        List<OrderResponseDto> orders = orderService.searchByEnp(enp);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/search/by-status/{status}")
    public ResponseEntity<List<OrderResponseDto>> searchByStatus(@PathVariable String status) {
        List<OrderResponseDto> orders = orderService.searchByStatus(status);
        return ResponseEntity.ok(orders);
    }

}
