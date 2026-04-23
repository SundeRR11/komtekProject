package com.example.komtekProject.dto;



public class OrderRequestDto {
    private Long patientId;
    private String comment;

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

}
