package com.example.komtekProject.dto;

import com.example.komtekProject.enums.OrderStatus;
import java.time.LocalDateTime;

public class OrderResponseDto {
    private Long id;
    private Long patientId;
    private String patientFullName;
    private String patientSnils;
    private String patientEnp;
    private LocalDateTime createdDate;
    private OrderStatus status;
    private String comment;

    public OrderResponseDto(Long id, Long patientId, String patientFullName,
                            String patientSnils, String patientEnp,
                            LocalDateTime createdAt, OrderStatus status, String comment) {
        this.id = id;
        this.patientId = patientId;
        this.patientFullName = patientFullName;
        this.patientSnils = patientSnils;
        this.patientEnp = patientEnp;
        this.createdDate = createdAt;
        this.status = status;
        this.comment = comment;

    }

    public Long getId() { return id; }
    public Long getPatientId() { return patientId; }
    public String getPatientFullName() { return patientFullName; }
    public String getPatientSnils() { return patientSnils; }
    public String getPatientEnp() { return patientEnp; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public OrderStatus getStatus() { return status; }
    public String getComment() { return comment; }

}
