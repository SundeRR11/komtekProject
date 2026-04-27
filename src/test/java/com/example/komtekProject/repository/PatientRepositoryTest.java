package com.example.komtekProject.repository;

import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
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
    void findById_ShouldReturnPatient() {
        var found = patientRepository.findById(testPatient.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getLastName()).isEqualTo("Иванов");
    }

    @Test
    void duplicateSnils_ShouldThrowException() {
        // Создаем второго пациента с таким же СНИЛС
        Patient patient2 = new Patient();
        patient2.setLastName("Петров");
        patient2.setFirstName("Петр");
        patient2.setBirthDate(LocalDate.of(1985, 5, 20));
        patient2.setGender(Gender.MALE);
        patient2.setSnils("123-456-789 01");  // ← такой же СНИЛС

        // Должно выбросить исключение
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            patientRepository.save(patient2);
        });
    }
}