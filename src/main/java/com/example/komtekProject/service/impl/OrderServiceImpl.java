package com.example.komtekProject.service.impl;


import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.dto.OrderSearchDto;
import com.example.komtekProject.entity.Order;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.OrderStatus;
import com.example.komtekProject.exception.OrderNotFoundException;
import com.example.komtekProject.exception.PatientNotFoundException;
import com.example.komtekProject.repository.OrderRepository;
import com.example.komtekProject.repository.PatientRepository;
import com.example.komtekProject.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(request.getPatientId()));
        Order order = new Order(patient, OrderStatus.REGISTERED, request.getComment());
        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return convertToDto(order);
    }

    @Override
    public List<OrderResponseDto> search(OrderSearchDto searchDto) {
        Long id = searchDto.getId();
        OrderStatus status = searchDto.getStatus() != null
                ? OrderStatus.valueOf(searchDto.getStatus().toUpperCase())
                : null;
        String snils = searchDto.getPatientSnils();
        String enp = searchDto.getPatientEnp();
        String fullName = searchDto.getPatientFullName();
        LocalDate birthDate = searchDto.getPatientBirthDate();

        List<Order> orders = orderRepository.universalSearch(id, status, snils, enp, fullName, birthDate);
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private OrderResponseDto convertToDto(Order order) {
        Patient patient = order.getPatient();

        String fullName = String.format("%s %s %s",
                patient.getLastName(),
                patient.getFirstName(),
                patient.getMiddleName() != null ? patient.getMiddleName() : "").trim();

        String enp = patient.getInsurancePolicy() != null ?
                patient.getInsurancePolicy().getPolicyNumber() : null;

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






}
