package com.example.komtekProject.repository;

import com.example.komtekProject.entity.Order;
import com.example.komtekProject.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {
            "patient",
            "patient.insurancePolicy",
            "creatorOrganization",
            "executorOrganization"
    })
    @Query("SELECT o FROM Order o WHERE " +
            "(:id IS NULL OR o.id = :id) AND " +
            "(:status IS NULL OR o.status = :status) AND " +
            "(:snils IS NULL OR o.patient.snils = :snils) AND " +
            "(:enp IS NULL OR o.patient.insurancePolicy.policyNumber = :enp) AND " +
            "(:fullName IS NULL OR LOWER(CONCAT(o.patient.lastName, ' ', o.patient.firstName, ' ', " +
            "COALESCE(o.patient.middleName, ''))) LIKE LOWER(CONCAT('%', :fullName, '%'))) AND " +
            "(:birthDate IS NULL OR o.patient.birthDate = :birthDate)")
    Page<Order> search(@Param("id") Long id,
                                     @Param("status") OrderStatus status,
                                     @Param("snils") String snils,
                                     @Param("enp") String enp,
                                     @Param("fullName") String fullName,
                                     @Param("birthDate") LocalDate birthDate,
                                     Pageable pageable);

}
