//package com.example.komtekProject.service;
//
//import com.example.komtekProject.entity.Patient;
//import com.example.komtekProject.enums.Gender;
//import com.example.komtekProject.exception.PatientNotFoundException;
//import com.example.komtekProject.repository.InsurancePolicyRepository;
//import com.example.komtekProject.repository.PatientRepository;
//import com.example.komtekProject.service.impl.PatientServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import java.time.LocalDate;
//import java.util.Optional;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class PatientServiceImplTest {
//
//    @Mock
//    private PatientRepository patientRepository;
//
//    @Mock
//    private InsurancePolicyRepository insurancePolicyRepository;
//
//    @InjectMocks
//    private PatientServiceImpl patientServiceImpl;
//
//    private Patient testPatient;
//
//    @BeforeEach
//    void setUp() {
//        testPatient = new Patient();
//        testPatient.setId(1L);
//        testPatient.setLastName("Иванов");
//        testPatient.setFirstName("Иван");
//        testPatient.setMiddleName("Иванович");
//        testPatient.setBirthDate(LocalDate.of(1990, 1, 15));
//        testPatient.setGender(Gender.MALE);
//        testPatient.setSnils("123-456-789 01");
//    }
//
////    @Test
////    void getPatientById_ShouldReturnPatient() {
////        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
////
////        Patient result = patientService.getPatientById(1L);
////
////        assertThat(result).isNotNull();
////        assertThat(result.getLastName()).isEqualTo("Иванов");
////    }
//
//    @Test
//    void getPatientById_WhenNotFound_ShouldThrowException() {
//        when(patientRepository.findById(999L)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> patientServiceImpl.getPatientById(999L))
//                .isInstanceOf(PatientNotFoundException.class)
//                .hasMessageContaining("Пациент с ID 999 не найден");
//    }
//
//}