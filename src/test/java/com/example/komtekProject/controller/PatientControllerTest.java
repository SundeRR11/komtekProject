package com.example.komtekProject.controller;

import com.example.komtekProject.dto.PatientResponseDto;
import com.example.komtekProject.enums.Gender;
import com.example.komtekProject.exception.GlobalExceptionHandler;
import com.example.komtekProject.exception.PatientNotFoundException;
import com.example.komtekProject.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private MockMvc mockMvc;
    private PatientResponseDto testPatientResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(patientController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        testPatientResponse = new PatientResponseDto(
                1L, "Иванов", "Иван", "Иванович",
                LocalDate.of(1990, 1, 1),
                Gender.MALE,
                "123-456-789 01"
        );
    }

    @Test
    void getPatientById_ShouldReturnPatient() throws Exception {
        when(patientService.getPatientById(1L)).thenReturn(testPatientResponse);

        mockMvc.perform(get("/api/v1/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.lastName").value("Иванов"))
                .andExpect(jsonPath("$.firstName").value("Иван"))
                .andExpect(jsonPath("$.gender").value("MALE"));
    }

    @Test
    void getPatientById_NotFound_ShouldReturnError() throws Exception {
        when(patientService.getPatientById(999L))
                .thenThrow(new PatientNotFoundException(999L));

        mockMvc.perform(get("/api/v1/patients/{id}", 999L))
                .andExpect(status().isBadRequest())  // ← 400, не 500
                .andExpect(jsonPath("$.errors[0].code").value("PATIENT_NOT_FOUND"))
                .andExpect(jsonPath("$.errors[0].message").value("Пациент с ID 999 не найден"));
    }
}