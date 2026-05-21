package com.example.komtekProject.controller;

import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.dto.OrderSearchDto;
import com.example.komtekProject.dto.OrderUpdateDto;
import com.example.komtekProject.dto.ResearchListResponseDto;
import com.example.komtekProject.dto.ResearchResultUploadDto;
import com.example.komtekProject.dto.StatusUpdateDto;
import com.example.komtekProject.service.OrderService;
import com.example.komtekProject.service.ResearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;
    private final ResearchService researchService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto request) {
        log.info("Создание заявки для пациента ID: {}, исследований: {}",
                request.getPatientId(), request.getResearches().size());
        OrderResponseDto order = orderService.createOrder(request);
        log.info("Заявка создана: ID={}", order.getId());
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        log.info("Получение заявки по ID: {}", id);
        OrderResponseDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrderResponseDto>> searchOrders(@Valid @ModelAttribute OrderSearchDto searchDto) {
        log.info("Поиск заявок с параметрами: {}", searchDto);
        Page<OrderResponseDto> orders = orderService.search(searchDto);
        log.info("Найдено заявок: всего={}, страниц={}", orders.getTotalElements(), orders.getTotalPages());
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable @Positive(message = "ID должен быть положительным") Long id,
            @Valid @RequestBody OrderUpdateDto updateDto) {

        log.info("PATCH запрос на обновление заявки ID: {}", id);
        OrderResponseDto updatedOrder = orderService.updateOrder(id, updateDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable @Positive Long id,
            @Valid @RequestBody StatusUpdateDto statusDto) {

        log.info("PATCH смена статуса для заявки ID: {} -> {}", id, statusDto.getStatus());
        OrderResponseDto updatedOrder = orderService.updateOrderStatus(id, statusDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{id}/researches")
    public ResponseEntity<ResearchListResponseDto> getResearches(@PathVariable @Positive Long id) {
        log.info("Получение исследований для заявки ID: {}", id);
        ResearchListResponseDto result = researchService.getResearchesByOrderId(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/researches")
    public ResponseEntity<ResearchListResponseDto> uploadResults(
            @PathVariable @Positive Long id,
            @Valid @RequestBody ResearchResultUploadDto uploadDto) {

        log.info("Загрузка результатов для заявки ID: {}", id);
        ResearchListResponseDto result = researchService.uploadResults(id, uploadDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.info("DELETE запрос на удаление заявки ID: {}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}