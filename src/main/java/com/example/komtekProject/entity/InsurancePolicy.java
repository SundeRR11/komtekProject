package com.example.komtekProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "insurance_policy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", unique = true)
    @JsonIgnore
    private  Patient patient;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "policy_number", nullable = false, unique = true, length = 16)
    private String policyNumber;

    public InsurancePolicy(Patient patient, String policyNumber) {
        this.patient = patient;
        this.policyNumber = policyNumber;
        this.createdDate = LocalDateTime.now();
    }

}
