package com.example.komtekProject.service.impl;

import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.dto.OrderSearchDto;
import com.example.komtekProject.dto.OrderUpdateDto;
import com.example.komtekProject.entity.Order;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.OrderStatus;
import com.example.komtekProject.exception.InvalidOrderUpdateException;
import com.example.komtekProject.exception.OrderNotFoundException;
import com.example.komtekProject.exception.PatientNotFoundException;
import com.example.komtekProject.mapper.OrderMapper;
import com.example.komtekProject.repository.OrderRepository;
import com.example.komtekProject.repository.PatientRepository;
import com.example.komtekProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PatientRepository patientRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request) {
        log.debug("Создание заявки для пациента ID: {}", request.getPatientId());

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> {
                    log.warn("Пациент с ID {} не найден", request.getPatientId());
                    return new PatientNotFoundException(request.getPatientId());
                });

        Order order = new Order(patient, OrderStatus.REGISTERED, request.getComment());
        Order savedOrder = orderRepository.save(order);

        log.debug("Заявка создана. ID: {}, пациент ID: {}, статус: {}", savedOrder.getId(), patient.getId(), savedOrder.getStatus());

        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long id) {
        log.debug("Поиск заявки по ID: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Заявка с ID {} не найдена", id);
                    return new OrderNotFoundException(id);
                });

        log.debug("Заявка найдена. ID: {}, статус: {}", id, order.getStatus());
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> search(OrderSearchDto searchDto) {
        log.debug("Поиск заявок с параметрами: {}", searchDto);

        Long id = searchDto.getId();
        OrderStatus status = searchDto.getStatus() != null
                ? OrderStatus.valueOf(searchDto.getStatus().toUpperCase())
                : null;
        String snils = searchDto.getPatientSnils();
        String enp = searchDto.getPatientEnp();
        String fullName = searchDto.getPatientFullName();
        LocalDate birthDate = searchDto.getPatientBirthDate();

        Sort.Direction direction = searchDto.getSortDir().equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(
                searchDto.getPage(),
                searchDto.getSize(),
                Sort.by(direction, searchDto.getSortBy())
        );

        Page<Order> orderPage = orderRepository.search(id, status, snils, enp, fullName, birthDate, pageable);
        log.debug("Найдено заявок: всего={}, страниц={}", orderPage.getTotalElements(), orderPage.getTotalPages());
        return orderPage.map(orderMapper::toDto);
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrder(Long id, OrderUpdateDto updateDto) {
        log.info("PATCH обновление заявки ID: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Заявка с ID {} не найдена для обновления", id);
                    return new OrderNotFoundException(id);
                });

        OrderStatus oldStatus = order.getStatus();
        String oldComment = order.getComment();
        boolean hasChanges = false;

        if (updateDto.getStatus() != null) {
            log.info("  Обновление статуса: {} -> {}", oldStatus, updateDto.getStatus());
            order.setStatus(updateDto.getStatus());
            hasChanges = true;
        }

        if (updateDto.getComment() != null) {
            log.info("  Обновление комментария: '{}' -> '{}'", oldComment, updateDto.getComment());
            order.setComment(updateDto.getComment());
            hasChanges = true;
        }

        if (!hasChanges) {
            log.warn("PATCH запрос для заявки ID: {} не содержит полей для обновления", id);
            throw new InvalidOrderUpdateException("Не указаны поля для обновления (status или comment)");
        }

        Order updatedOrder = orderRepository.save(order);
        log.info("Заявка ID: {} успешно обновлена", updatedOrder.getId());

        return orderMapper.toDto(updatedOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        log.info("Удаление заявки ID: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Заявка с ID {} не найдена для удаления", id);
                    return new OrderNotFoundException(id);
                });

        Long orderId = order.getId();
        OrderStatus status = order.getStatus();
        Long patientId = order.getPatient().getId();

        orderRepository.delete(order);
        log.info("Заявка ID: {} удалена. Статус: {}, Пациент ID: {}", orderId, status, patientId);
    }
}







