package com.example.komtekProject.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class OrderRequestDto {
    @NotNull(message = "ID пациента обязателен")
    @Positive(message = "ID пациента должен быть положительным числом")
    private Long patientId;

    @Size(max = 500, message = "Комментарий не может превышать 500 символов")
    private String comment;

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

}
