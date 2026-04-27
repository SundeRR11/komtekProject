package com.example.komtekProject.dto;

import java.time.LocalDateTime;

public class InsurancePolicyResponseDto {
    private Long id;
    private Long patientId;
    private LocalDateTime createdDate;
    private String policyNumber;

    public InsurancePolicyResponseDto(Long id, Long patientId, LocalDateTime createdDate, String policyNumber) {
        this.id = id;
        this.patientId = patientId;
        this.createdDate = createdDate;
        this.policyNumber = policyNumber;
    }


    public String getPolicyNumber() {
        return policyNumber;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getId() {
        return id;
    }
}