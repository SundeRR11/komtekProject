package com.example.komtekProject.service;


import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.dto.OrderSearchDto;
import com.example.komtekProject.dto.OrderUpdateDto;
import org.springframework.data.domain.Page;


public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto request);

    OrderResponseDto getOrderById(Long id);

    Page<OrderResponseDto> search(OrderSearchDto searchDto);

    OrderResponseDto updateOrder(Long id, OrderUpdateDto updateDto);

    void deleteOrder(Long id);
}
