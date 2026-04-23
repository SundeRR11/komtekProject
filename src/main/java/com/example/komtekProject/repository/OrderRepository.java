package com.example.komtekProject.repository;

import com.example.komtekProject.entity.Order;
import com.example.komtekProject.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByPatientId(Long Id);

    @Query("SELECT u from Order u WHERE u.patient.snils = :snils")
    List<Order> findByPatientSnils(@Param("snils") String snils);

    @Query("SELECT u FROM Order u WHERE u.patient.insurancePolicy.policyNumber = :enp")
    List<Order> findByPatientEnp(@Param("enp") String enp);

    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT u FROM Order u WHERE " +
            "LOWER(CONCAT(u.patient.lastName, ' ', u.patient.firstName, ' ', COALESCE(u.patient.middleName, ''))) " +
            "LIKE LOWER(CONCAT('%', :fullName, '%')) AND u.patient.birthDate = :birthDate")
    List<Order> findByPatientFullNameAndBirthDate(@Param("fullName") String fullName,
                                                  @Param("birthDate") LocalDate birthDate);



}
