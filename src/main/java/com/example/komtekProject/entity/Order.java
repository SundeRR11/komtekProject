package com.example.komtekProject.entity;

import com.example.komtekProject.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_org_id", nullable = false)
    private MedicalOrganization creatorOrganization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_org_id", nullable = false)
    private MedicalOrganization executorOrganization;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(length = 500)
    private String comment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Research> researches = new ArrayList<>();

    public Order(Patient patient,
                 MedicalOrganization creatorOrganization,
                 MedicalOrganization executorOrganization,
                 OrderStatus status,
                 String comment) {
        this.patient = patient;
        this.creatorOrganization = creatorOrganization;
        this.executorOrganization = executorOrganization;
        this.status = status;
        this.comment = comment;
        this.createdDate = LocalDateTime.now();
    }

    public void addResearch(Research research) {
        researches.add(research);
        research.setOrder(this);
    }
}