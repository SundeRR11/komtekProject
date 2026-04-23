package com.example.komtekProject.service;


import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.entity.Order;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.OrderStatus;
import com.example.komtekProject.repository.OrderRepository;
import com.example.komtekProject.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private PatientRepository patientRepository;

    public OrderService(OrderRepository orderRepository, PatientRepository patientRepository) {
        this.orderRepository = orderRepository;
        this.patientRepository = patientRepository;
    }

    public OrderResponseDto createOrder(OrderRequestDto request) {
        Patient patient = patientRepository.findPatientById(request.getPatientId());
        Order order = new Order(patient, OrderStatus.REGISTERED, request.getComment());
        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).get();
        return convertToDto(order);
    }

    public OrderResponseDto searchById(Long id) {
        Order order = orderRepository.findById(id).get();
        return convertToDto(order);
    }

    public List<OrderResponseDto> searchByFullNameAndBirthDate(String fullName, String birthDateStr) {
        LocalDate birthDate = LocalDate.parse(birthDateStr);
        List<Order> orders = orderRepository.findByPatientFullNameAndBirthDate(fullName, birthDate);
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<OrderResponseDto> searchBySnils(String snils) {
        List<Order> orders = orderRepository.findByPatientSnils(snils);
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<OrderResponseDto> searchByEnp(String enp) {
        List<Order> orders = orderRepository.findByPatientEnp(enp);
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<OrderResponseDto> searchByStatus(String statusStr) {
        OrderStatus status = OrderStatus.valueOf(statusStr.toUpperCase());
        List<Order> orders = orderRepository.findByStatus(status);
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
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
