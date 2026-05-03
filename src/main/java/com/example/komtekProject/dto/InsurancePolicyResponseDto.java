package com.example.komtekProject.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Getter
@Setter
public class InsurancePolicyResponseDto {
    private final Long id;
    private final Long patientId;
    private final LocalDateTime createdDate;
    private final String policyNumber;

}