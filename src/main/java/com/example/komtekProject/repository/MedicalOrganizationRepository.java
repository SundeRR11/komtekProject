package com.example.komtekProject.repository;

import com.example.komtekProject.entity.MedicalOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalOrganizationRepository extends JpaRepository<MedicalOrganization, Long> {
}