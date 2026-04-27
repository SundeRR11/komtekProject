package com.example.komtekProject.controller;

import com.example.komtekProject.entity.InsurancePolicy;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.Gender;
import com.example.komtekProject.repository.InsurancePolicyRepository;
import com.example.komtekProject.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        insurancePolicyRepository.deleteAll();
        patientRepository.deleteAll();

        testPatient = new Patient();
        testPatient.setLastName("Петров");
        testPatient.setFirstName("Петр");
        testPatient.setMiddleName("Петрович");
        testPatient.setBirthDate(LocalDate.of(1985, 5, 20));
        testPatient.setGender(Gender.MALE);
        testPatient.setSnils("987-654-321 01");
        patientRepository.save(testPatient);
    }

    @Test
    void getPatientById_ShouldReturnPatient() throws Exception {
        mockMvc.perform(get("/api/v1/patients/" + testPatient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testPatient.getId()))
                .andExpect(jsonPath("$.lastName").value("Петров"))
                .andExpect(jsonPath("$.firstName").value("Петр"));
    }

    @Test
    void getPatientById_NotFound_ShouldReturnError() throws Exception {
        mockMvc.perform(get("/api/v1/patients/99999"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void addPolicyToPatient_ShouldReturnPolicy() throws Exception {
        mockMvc.perform(post("/api/v1/patients/" + testPatient.getId() + "/policy")
                        .param("policyNumber", "1111222233334444"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.policyNumber").value("1111222233334444"));
    }

    @Test
    void getPolicyByPatient_ShouldReturnPolicy() throws Exception {
        InsurancePolicy policy = new InsurancePolicy(testPatient, "9999888877776666");
        insurancePolicyRepository.save(policy);
        testPatient.setInsurancePolicy(policy);
        patientRepository.save(testPatient);

        mockMvc.perform(get("/api/v1/patients/" + testPatient.getId() + "/policy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.policyNumber").value("9999888877776666"));
    }
}