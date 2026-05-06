package com.example.komtekProject.mapper.Impl;

import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.entity.Order;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.mapper.OrderMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponseDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        Patient patient = order.getPatient();

        String fullName = String.format("%s %s %s",
                patient.getLastName() != null ? patient.getLastName() : "",
                patient.getFirstName() != null ? patient.getFirstName() : "",
                patient.getMiddleName() != null ? patient.getMiddleName() : "").trim();

        String enp = patient.getInsurancePolicy() != null
                ? patient.getInsurancePolicy().getPolicyNumber()
                : null;

        return new OrderResponseDto(
                order.getId(),
                patient.getId(),
                fullName,
                patient.getSnils(),
                enp,
                order.getCreatedDate(),
                order.getStatus(),
                order.getComment()
        );
    }

    @Override
    public List<OrderResponseDto> toDtoList(List<Order> orders) {
        if (orders == null) {
            return null;
        }

        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}