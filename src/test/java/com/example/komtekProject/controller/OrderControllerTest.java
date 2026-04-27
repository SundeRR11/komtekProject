package com.example.komtekProject.controller;

import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.Gender;
import com.example.komtekProject.repository.OrderRepository;
import com.example.komtekProject.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        patientRepository.deleteAll();

        testPatient = new Patient();
        testPatient.setLastName("Иванов");
        testPatient.setFirstName("Иван");
        testPatient.setMiddleName("Иванович");
        testPatient.setBirthDate(LocalDate.of(1990, 1, 15));
        testPatient.setGender(Gender.MALE);
        testPatient.setSnils("123-456-789 01");
        patientRepository.save(testPatient);
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() throws Exception {
        OrderRequestDto request = new OrderRequestDto();
        request.setPatientId(testPatient.getId());
        request.setComment("Анализ крови");

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value(testPatient.getId()))
                .andExpect(jsonPath("$.status").value("REGISTERED"))
                .andExpect(jsonPath("$.comment").value("Анализ крови"));
    }

    @Test
    void getOrderById_ShouldReturnOrder() throws Exception {
        OrderRequestDto request = new OrderRequestDto();
        request.setPatientId(testPatient.getId());
        request.setComment("Тест");

        String response = mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long orderId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/v1/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId));
    }

    @Test
    void searchBySnils_ShouldReturnOrders() throws Exception {
        mockMvc.perform(get("/api/v1/orders/search/by-snils/123-456-789 01"))
                .andExpect(status().isOk());
    }

    @Test
    void searchByStatus_ShouldReturnOrders() throws Exception {
        mockMvc.perform(get("/api/v1/orders/search/by-status/REGISTERED"))
                .andExpect(status().isOk());
    }

    @Test
    void createOrder_WithInvalidPatientId_ShouldReturnBadRequest() throws Exception {
        OrderRequestDto request = new OrderRequestDto();
        request.setPatientId(99999L);  // Несуществующий пациент
        request.setComment("Тест");

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrder_WithNullPatientId_ShouldReturnBadRequest() throws Exception {
        OrderRequestDto request = new OrderRequestDto();
        request.setPatientId(null);
        request.setComment("Тест");

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}