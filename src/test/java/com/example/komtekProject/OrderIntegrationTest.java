package com.example.komtekProject;

import com.example.komtekProject.dto.OrderRequestDto;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.Gender;
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
class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientRepository patientRepository;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();

        testPatient = new Patient();
        testPatient.setLastName("Тестов");
        testPatient.setFirstName("Тест");
        testPatient.setBirthDate(LocalDate.of(2000, 1, 1));
        testPatient.setGender(Gender.MALE);
        testPatient.setSnils("111-222-333 44");  // ← уникальный
        patientRepository.save(testPatient);
    }

    @Test
    void createAndGetOrder_IntegrationTest() throws Exception {
        OrderRequestDto request = new OrderRequestDto();
        request.setPatientId(testPatient.getId());
        request.setComment("Интеграционный тест");

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Интеграционный тест"));
    }
}