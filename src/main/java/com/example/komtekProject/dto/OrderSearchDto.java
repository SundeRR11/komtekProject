package com.example.komtekProject.dto;


public class OrderSearchDto {
    private Long id;
    private String patientFullName;
    private String patientBirthDate;
    private String patientSnils;
    private String patientEnp;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientFullName() { return patientFullName; }
    public void setPatientFullName(String patientFullName) { this.patientFullName = patientFullName; }

    public String getPatientBirthDate() { return patientBirthDate; }
    public void setPatientBirthDate(String patientBirthDate) { this.patientBirthDate = patientBirthDate; }

    public String getPatientSnils() { return patientSnils; }
    public void setPatientSnils(String patientSnils) { this.patientSnils = patientSnils; }

    public String getPatientEnp() { return patientEnp; }
    public void setPatientEnp(String patientEnp) { this.patientEnp = patientEnp; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
