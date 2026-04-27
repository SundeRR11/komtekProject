package com.example.komtekProject.repository;

import com.example.komtekProject.entity.Order;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.Gender;
import com.example.komtekProject.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PatientRepository patientRepository;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        patientRepository.deleteAll();

        testPatient = new Patient();
        testPatient.setLastName("Иванов");
        testPatient.setFirstName("Иван");
        testPatient.setBirthDate(LocalDate.of(1990, 1, 15));
        testPatient.setGender(Gender.MALE);
        testPatient.setSnils("123-456-789 01");
        patientRepository.save(testPatient);

        Order order1 = new Order(testPatient, OrderStatus.REGISTERED, "Анализ крови");
        order1.setCreatedDate(LocalDateTime.now());
        Order order2 = new Order(testPatient, OrderStatus.COMPLETED, "УЗИ");
        order2.setCreatedDate(LocalDateTime.now());
        orderRepository.saveAll(List.of(order1, order2));
    }

    @Test
    void findByPatientSnils_ShouldReturnOrders() {
        List<Order> orders = orderRepository.findByPatientSnils("123-456-789 01");
        assertThat(orders).hasSize(2);
    }
}