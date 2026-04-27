package com.example.komtekProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "insurancePolicy")
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", unique = true)
    @JsonIgnore
    private Patient patient;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "policy_number", nullable = false, unique = true, length = 16)
    private String policyNumber;

    public InsurancePolicy(){}

    public InsurancePolicy(Patient patient, String policyNumber) {
        this.patient = patient;
        this.policyNumber = policyNumber;
        this.createdDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }


}
