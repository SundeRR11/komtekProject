package com.example.komtekProject.repository;

import com.example.komtekProject.entity.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {

    InsurancePolicy findByPatientId(Long patientId);

    InsurancePolicy findByPolicyNumber(String policyNumber);
}
