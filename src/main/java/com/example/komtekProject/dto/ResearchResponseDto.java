package com.example.komtekProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResearchResponseDto {
    private Long id;
    private String name;
    private BigDecimal numberResult;
    private String unit;
    private String textResult;
    private String conclusion;
    private LocalDateTime registrationDate;
}