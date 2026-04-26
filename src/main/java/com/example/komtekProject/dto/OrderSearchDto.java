package com.example.komtekProject.dto;


import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class OrderSearchDto {
    @Positive(message = "ID должен быть положительным числом")
    private Long id;

    @Size(max = 200, message = "ФИО не может превышать 200 символов")
    private String patientFullName;

    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate patientBirthDate;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3} \\d{2}$", message = "СНИЛС должен быть в формате 123-456-789 01")
    private String patientSnils;

    @Pattern(regexp = "^\\d{16}$", message = "ЕНП должен состоять из 16 цифр")
    private String patientEnp;

    @Pattern(regexp = "^(REGISTERED|IN_PROGRESS|COMPLETED|CANCELED)$",
            message = "Статус должен быть: REGISTERED, IN_PROGRESS, COMPLETED, CANCELED")
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientFullName() { return patientFullName; }
    public void setPatientFullName(String patientFullName) { this.patientFullName = patientFullName; }

    public LocalDate getPatientBirthDate() { return patientBirthDate; }
    public void setPatientBirthDate(LocalDate patientBirthDate) { this.patientBirthDate = patientBirthDate; }

    public String getPatientSnils() { return patientSnils; }
    public void setPatientSnils(String patientSnils) { this.patientSnils = patientSnils; }

    public String getPatientEnp() { return patientEnp; }
    public void setPatientEnp(String patientEnp) { this.patientEnp = patientEnp; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
