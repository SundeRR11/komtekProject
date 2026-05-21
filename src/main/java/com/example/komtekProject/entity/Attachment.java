package com.example.komtekProject.entity;

import com.example.komtekProject.enums.AttachmentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mo_id", nullable = false)
    private MedicalOrganization medicalOrganization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttachmentType type;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    public Attachment(Patient patient, MedicalOrganization medicalOrganization, AttachmentType type) {
        this.patient = patient;
        this.medicalOrganization = medicalOrganization;
        this.type = type;
        this.createdDate = LocalDateTime.now();
    }
}