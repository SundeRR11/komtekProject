package com.example.komtekProject.controller;

import java.time.LocalDate;
import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.dto.OrderSearchDto;
import com.example.komtekProject.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto request) {
        OrderResponseDto order = orderServiceImpl.createOrder(request);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        OrderResponseDto order = orderServiceImpl.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrderResponseDto>> searchOrders(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String birthDate,
            @RequestParam(required = false) String snils,
            @RequestParam(required = false) String enp,
            @RequestParam(required = false) String status) {

        OrderSearchDto searchDto = new OrderSearchDto();
        searchDto.setId(id);
        searchDto.setPatientFullName(fullName);

        if (birthDate != null && !birthDate.isEmpty()) {
            searchDto.setPatientBirthDate(LocalDate.parse(birthDate));
        }

        searchDto.setPatientSnils(snils);
        searchDto.setPatientEnp(enp);
        searchDto.setStatus(status);

        List<OrderResponseDto> orders = orderServiceImpl.search(searchDto);
        return ResponseEntity.ok(orders);
    }


}
