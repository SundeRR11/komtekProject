package com.example.komtekProject.dto;

import com.example.komtekProject.enums.OrderStatus;

import java.time.LocalDate;

public class OrderResponseDto {
    private Long id;
    private Long patientId;
    private String patientFullName;
    private String patientSnils;
    private String patientEnp;
    private LocalDate createdDate;
    private OrderStatus status;
    private String comment;

    public OrderResponseDto(Long id, Long patientId, String patientFullName,
                            String patientSnils, String patientEnp,
                            LocalDate createdDate, OrderStatus status, String comment) {
        this.id = id;
        this.patientId = patientId;
        this.patientFullName = patientFullName;
        this.patientSnils = patientSnils;
        this.patientEnp = patientEnp;
        this.createdDate = createdDate;
        this.status = status;
        this.comment = comment;

    }
}
