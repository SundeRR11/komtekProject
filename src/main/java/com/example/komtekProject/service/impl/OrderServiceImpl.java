package com.example.komtekProject.service.impl;

import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.dto.OrderSearchDto;
import com.example.komtekProject.entity.Order;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.OrderStatus;
import com.example.komtekProject.exception.OrderNotFoundException;
import com.example.komtekProject.exception.PatientNotFoundException;
import com.example.komtekProject.mapper.OrderMapper;
import com.example.komtekProject.repository.OrderRepository;
import com.example.komtekProject.repository.PatientRepository;
import com.example.komtekProject.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PatientRepository patientRepository;
    private final OrderMapper orderMapper ;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(request.getPatientId()));
        Order order = new Order(patient, OrderStatus.REGISTERED, request.getComment());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
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

        return orderMapper.toDtoList(orders);
    }
}







