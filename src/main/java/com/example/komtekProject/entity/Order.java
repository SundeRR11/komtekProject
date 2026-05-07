package com.example.komtekProject.entity;

import com.example.komtekProject.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Column(name = "created_date",nullable = false)
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(length = 500)
    private String comment;

    public Order(Patient patient, OrderStatus status, String comment) {
        this.patient = patient;
        this.status = status;
        this.comment = comment;
        this.createdDate = LocalDateTime.now();
    }

}
