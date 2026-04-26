package com.example.komtekProject.repository;

import com.example.komtekProject.entity.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {

    Optional<InsurancePolicy> findByPatientId(Long patientId);

    Optional<InsurancePolicy> findByPolicyNumber(String policyNumber);
}
