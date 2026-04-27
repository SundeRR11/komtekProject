package com.example.komtekProject.repository;

import com.example.komtekProject.entity.InsurancePolicy;
import com.example.komtekProject.entity.Order;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.enums.Gender;
import com.example.komtekProject.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        insurancePolicyRepository.deleteAll();
        patientRepository.deleteAll();

        testPatient = new Patient();
        testPatient.setLastName("Иванов");
        testPatient.setFirstName("Иван");
        testPatient.setMiddleName("Иванович");
        testPatient.setBirthDate(LocalDate.of(1990, 1, 15));
        testPatient.setGender(Gender.MALE);
        testPatient.setSnils("123-456-789 01");
        patientRepository.save(testPatient);

        InsurancePolicy policy = new InsurancePolicy(testPatient, "1234567890123456");
        insurancePolicyRepository.save(policy);
        testPatient.setInsurancePolicy(policy);
        patientRepository.save(testPatient);

        Order order1 = new Order(testPatient, OrderStatus.REGISTERED, "Анализ крови");
        order1.setCreatedDate(LocalDateTime.now());
        Order order2 = new Order(testPatient, OrderStatus.REGISTERED, "УЗИ");
        order2.setCreatedDate(LocalDateTime.now());
        orderRepository.save(order1);
        orderRepository.save(order2);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void universalSearch_ShouldReturnFilteredOrders() {
        List<Order> orders = orderRepository.universalSearch(
                null, OrderStatus.REGISTERED, "123-456-789 01", null, null, null);

        assertThat(orders).isNotEmpty();
        assertThat(orders).hasSize(2);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void findByPatientEnp_ShouldReturnOrders() {
        List<Order> orders = orderRepository.findByPatientEnp("1234567890123456");
        assertThat(orders).isNotEmpty();
        assertThat(orders).hasSize(2);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findByPatientSnils_ShouldReturnOrders() {
        List<Order> orders = orderRepository.findByPatientSnils("123-456-789 01");
        assertThat(orders).isNotEmpty();
        assertThat(orders).hasSize(2);
    }
}