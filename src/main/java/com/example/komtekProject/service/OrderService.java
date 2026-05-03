package com.example.komtekProject.service;


import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.dto.OrderSearchDto;
import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto request);

    OrderResponseDto getOrderById(Long id);

    List<OrderResponseDto> search(OrderSearchDto searchDto);
}
