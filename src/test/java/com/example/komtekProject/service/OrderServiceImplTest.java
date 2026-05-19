//package com.example.komtekProject.service;
//
//import com.example.komtekProject.dto.OrderRequestDto;
//import com.example.komtekProject.dto.OrderResponseDto;
//import com.example.komtekProject.dto.OrderSearchDto;
//import com.example.komtekProject.entity.Order;
//import com.example.komtekProject.entity.Patient;
//import com.example.komtekProject.enums.OrderStatus;
//import com.example.komtekProject.exception.OrderNotFoundException;
//import com.example.komtekProject.exception.PatientNotFoundException;
//import com.example.komtekProject.mapper.OrderMapper;
//import com.example.komtekProject.repository.OrderRepository;
//import com.example.komtekProject.repository.PatientRepository;
//import com.example.komtekProject.service.impl.OrderServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class OrderServiceImplTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private PatientRepository patientRepository;
//
//    @Mock
//    private OrderMapper orderMapper;
//
//    @InjectMocks
//    private OrderServiceImpl orderService;
//
//    private Patient testPatient;
//    private Order testOrder;
//    private OrderResponseDto testResponseDto;
//
//    @BeforeEach
//    void setUp() {
//        testPatient = new Patient();
//        testPatient.setId(1L);
//        testPatient.setLastName("Иванов");
//        testPatient.setFirstName("Иван");
//        testPatient.setSnils("123-456-789 01");
//
//        testOrder = new Order(testPatient, OrderStatus.REGISTERED, "Тест");
//        testOrder.setId(1L);
//        testOrder.setCreatedDate(LocalDateTime.now());
//
//        testResponseDto = new OrderResponseDto(
//                1L, 1L, "Иванов Иван Иванович",
//                "123-456-789 01", "1234567890123456",
//                LocalDateTime.now(), OrderStatus.REGISTERED, "Тест"
//        );
//    }
//
//    @Test
//    void createOrder_ShouldReturnOrderResponseDto() {
//        OrderRequestDto request = new OrderRequestDto();
//        request.setPatientId(1L);
//        request.setComment("Тест");
//
//        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
//        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);
//        when(orderMapper.toDto(testOrder)).thenReturn(testResponseDto);
//
//        OrderResponseDto response = orderService.createOrder(request);
//
//        assertThat(response).isNotNull();
//        assertThat(response.getId()).isEqualTo(1L);
//    }
//
//    @Test
//    void createOrder_WhenPatientNotFound_ShouldThrowException() {
//        OrderRequestDto request = new OrderRequestDto();
//        request.setPatientId(999L);
//
//        when(patientRepository.findById(999L)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> orderService.createOrder(request))
//                .isInstanceOf(PatientNotFoundException.class);
//    }
//
//    @Test
//    void getOrderById_ShouldReturnOrder() {
//        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
//        when(orderMapper.toDto(testOrder)).thenReturn(testResponseDto);
//
//        OrderResponseDto response = orderService.getOrderById(1L);
//
//        assertThat(response).isNotNull();
//        assertThat(response.getId()).isEqualTo(1L);
//    }
//
//    @Test
//    void getOrderById_WhenOrderNotFound_ShouldThrowException() {
//        when(orderRepository.findById(999L)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> orderService.getOrderById(999L))
//                .isInstanceOf(OrderNotFoundException.class);
//    }
//
//    @Test
//    void search_ShouldReturnPageOfOrders() {
//        OrderSearchDto searchDto = new OrderSearchDto();
//        searchDto.setPatientSnils("123-456-789 01");
//        searchDto.setStatus("REGISTERED");
//        searchDto.setPage(0);
//        searchDto.setSize(10);
//
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Order> orderPage = new PageImpl<>(List.of(testOrder), pageable, 1);
//
//        when(orderRepository.search(any(), any(), any(), any(), any(), any(), any()))
//                .thenReturn(orderPage);
//        when(orderMapper.toDto(testOrder)).thenReturn(testResponseDto);
//
//        Page<OrderResponseDto> result = orderService.search(searchDto);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getTotalElements()).isEqualTo(1);
//        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
//    }
//}