package com.example.komtekProject.entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "insurancePolicy")
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", unique = true)
    private Patient patient;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "policy_number", nullable = false, unique = true, length = 16)
    private String policyNumber;



}
