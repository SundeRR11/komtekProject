package com.example.komtekProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medical_organizations")
@Getter
@Setter
@NoArgsConstructor
public class MedicalOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    public MedicalOrganization(String code, String name, String email) {
        this.code = code;
        this.name = name;
        this.email = email;
    }
}