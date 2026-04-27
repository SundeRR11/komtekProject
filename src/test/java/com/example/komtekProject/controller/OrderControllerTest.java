package com.example.komtekProject.controller;

import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.dto.OrderSearchDto;
import com.example.komtekProject.enums.OrderStatus;
import com.example.komtekProject.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;  // ← service (не orderRepository)

    @InjectMocks
    private OrderController orderController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private OrderResponseDto testResponse;
    private OrderRequestDto testRequest;
    private OrderSearchDto testSearchDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        testRequest = new OrderRequestDto();
        testRequest.setPatientId(1L);
        testRequest.setComment("Тестовая заявка");

        testResponse = new OrderResponseDto(
                1L, 1L, "Иванов Иван Иванович",
                "123-456-789 01", "1234567890123456",
                LocalDateTime.now(), OrderStatus.REGISTERED, "Тестовая заявка"
        );

        testSearchDto = new OrderSearchDto();
        testSearchDto.setPatientSnils("123-456-789 01");
        testSearchDto.setStatus("REGISTERED");
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() throws Exception {
        when(orderService.createOrder(any(OrderRequestDto.class))).thenReturn(testResponse);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getOrderById_ShouldReturnOrder() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(testResponse);

        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void searchBySnils_ShouldReturnOrders() throws Exception {
        when(orderService.searchBySnils("123-456-789 01")).thenReturn(List.of(testResponse));

        mockMvc.perform(get("/api/v1/orders/search/by-snils/123-456-789 01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientSnils").value("123-456-789 01"));
    }

    @Test
    void searchByStatus_ShouldReturnOrders() throws Exception {
        when(orderService.searchByStatus("REGISTERED")).thenReturn(List.of(testResponse));

        mockMvc.perform(get("/api/v1/orders/search/by-status/REGISTERED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("REGISTERED"));
    }

    @Test
    void searchByEnp_ShouldReturnOrders() throws Exception {
        when(orderService.searchByEnp("1234567890123456")).thenReturn(List.of(testResponse));

        mockMvc.perform(get("/api/v1/orders/search/by-enp/1234567890123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientEnp").value("1234567890123456"));
    }

    @Test
    void universalSearch_ShouldReturnOrders() throws Exception {
        when(orderService.universalSearch(any(OrderSearchDto.class))).thenReturn(List.of(testResponse));

        mockMvc.perform(post("/api/v1/orders/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testSearchDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}