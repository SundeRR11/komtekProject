package com.example.komtekProject.mapper;

import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.entity.Order;
import java.util.List;

public interface OrderMapper {

    OrderResponseDto toDto(Order order);

    List<OrderResponseDto> toDtoList(List<Order> orders);
}