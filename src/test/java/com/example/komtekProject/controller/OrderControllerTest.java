//package com.example.komtekProject.controller;
//
//import com.example.komtekProject.dto.OrderRequestDto;
//import com.example.komtekProject.dto.OrderResponseDto;
//import com.example.komtekProject.dto.OrderSearchDto;
//import com.example.komtekProject.enums.OrderStatus;
//import com.example.komtekProject.exception.GlobalExceptionHandler;
//import com.example.komtekProject.exception.OrderNotFoundException;
//import com.example.komtekProject.service.OrderService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import tools.jackson.databind.ObjectMapper;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//class OrderControllerTest {
//
//    @Mock
//    private OrderService orderService;
//
//    @InjectMocks
//    private OrderController orderController;
//
//    private MockMvc mockMvc;
//    private ObjectMapper objectMapper;
//
//    private OrderResponseDto testOrderResponse;
//    private OrderRequestDto testOrderRequest;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(orderController)
//                .setControllerAdvice(new GlobalExceptionHandler())
//                .build();
//        objectMapper = new ObjectMapper();
//
//        testOrderRequest = new OrderRequestDto();
//        testOrderRequest.setPatientId(1L);
//        testOrderRequest.setComment("Тестовый комментарий");
//
//        testOrderResponse = new OrderResponseDto(
//                1L, 1L, "Иванов Иван Иванович",
//                "123-456-789 01", "1234567890123456",
//                LocalDateTime.now(), OrderStatus.REGISTERED, "Тестовый комментарий"
//        );
//    }
//
//    @Test
//    void createOrder_ShouldReturnCreatedOrder() throws Exception {
//        when(orderService.createOrder(any(OrderRequestDto.class))).thenReturn(testOrderResponse);
//
//        mockMvc.perform(post("/api/v1/orders")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(testOrderRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.patientId").value(1L))
//                .andExpect(jsonPath("$.status").value("REGISTERED"));
//    }
//
//    @Test
//    void createOrder_WithInvalidPatientId_ShouldReturnBadRequest() throws Exception {
//        OrderRequestDto invalidRequest = new OrderRequestDto();
//        invalidRequest.setPatientId(null);
//
//        mockMvc.perform(post("/api/v1/orders")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidRequest)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void getOrderById_ShouldReturnOrder() throws Exception {
//        when(orderService.getOrderById(1L)).thenReturn(testOrderResponse);
//
//        mockMvc.perform(get("/api/v1/orders/{id}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.patientFullName").value("Иванов Иван Иванович"));
//    }
//
//    @Test
//    void getOrderById_NotFound_ShouldReturnError() throws Exception {
//        when(orderService.getOrderById(999L))
//                .thenThrow(new OrderNotFoundException(999L));
//
//        mockMvc.perform(get("/api/v1/orders/{id}", 999L))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.errors[0].code").value("ORDER_NOT_FOUND"))
//                .andExpect(jsonPath("$.errors[0].message").value("Заявка с ID 999 не найдена"));
//    }
//
//    @Test
//    void searchOrders_ShouldReturnPageOfOrders() throws Exception {
//        Page<OrderResponseDto> orderPage = new PageImpl<>(
//                List.of(testOrderResponse),
//                PageRequest.of(0, 10),
//                1
//        );
//
//        when(orderService.search(any(OrderSearchDto.class))).thenReturn(orderPage);
//
//        mockMvc.perform(get("/api/v1/orders/search")
//                        .param("patientFullName", "Иванов")
//                        .param("status", "REGISTERED"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].id").value(1L))
//                .andExpect(jsonPath("$.totalElements").value(1));
//    }
//}