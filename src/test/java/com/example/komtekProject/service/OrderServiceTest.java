package com.example.komtekProject.service;

import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.entity.Order;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.OrderStatus;
import com.example.komtekProject.exception.OrderNotFoundException;
import com.example.komtekProject.exception.PatientNotFoundException;
import com.example.komtekProject.repository.OrderRepository;
import com.example.komtekProject.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private OrderService orderService;

    private Patient testPatient;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        testPatient = new Patient();
        testPatient.setId(1L);
        testPatient.setLastName("Иванов");
        testPatient.setFirstName("Иван");
        testPatient.setSnils("123-456-789 01");

        testOrder = new Order(testPatient, OrderStatus.REGISTERED, "Тест");
        testOrder.setId(1L);
        testOrder.setCreatedDate(LocalDateTime.now());
    }

    @Test
    void createOrder_ShouldReturnOrderResponseDto() {
        OrderRequestDto request = new OrderRequestDto();
        request.setPatientId(1L);
        request.setComment("Тест");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        OrderResponseDto response = orderService.createOrder(request);

        assertThat(response).isNotNull();
        assertThat(response.getPatientId()).isEqualTo(1L);
    }

    @Test
    void createOrder_WhenPatientNotFound_ShouldThrowException() {
        OrderRequestDto request = new OrderRequestDto();
        request.setPatientId(999L);

        when(patientRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(PatientNotFoundException.class);
    }

    @Test
    void getOrderById_ShouldReturnOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        OrderResponseDto response = orderService.getOrderById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    void getOrderById_WhenOrderNotFound_ShouldThrowException() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(999L))
                .isInstanceOf(OrderNotFoundException.class);
    }
}