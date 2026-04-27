package com.example.komtekProject.repository;

import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        // Очищаем базу перед каждым тестом
        patientRepository.deleteAll();

        testPatient = new Patient();
        testPatient.setLastName("Иванов");
        testPatient.setFirstName("Иван");
        testPatient.setMiddleName("Иванович");
        testPatient.setBirthDate(LocalDate.of(1990, 1, 15));
        testPatient.setGender(Gender.MALE);
        testPatient.setSnils("123-456-789 01");  // ← уникальный SNILS
        patientRepository.save(testPatient);
    }

    @Test
    void findById_ShouldReturnPatient() {
        var found = patientRepository.findById(testPatient.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getLastName()).isEqualTo("Иванов");
    }
}