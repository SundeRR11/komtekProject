package com.example.komtekProject.dto;

import com.example.komtekProject.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Long patientId;
    private String patientFullName;
    private String patientSnils;
    private String patientEnp;
    private Long creatorOrgId;
    private String creatorOrgName;
    private Long executorOrgId;
    private String executorOrgName;
    private LocalDateTime createdDate;
    private OrderStatus status;
    private String comment;
}