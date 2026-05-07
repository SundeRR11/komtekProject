package com.example.komtekProject.mapper;

import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.lastName", target = "patientFullName")
    OrderResponseDto toDto(Order order);

    List<OrderResponseDto> toDtoList(List<Order> orders);
}