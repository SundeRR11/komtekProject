package com.example.komtekProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "research")
@Getter
@Setter
@NoArgsConstructor
public class Research {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String name;

    @Column(name = "number_result", precision = 15, scale = 4)
    private BigDecimal numberResult;

    @Column(length = 50)
    private String unit;

    @Column(name = "text_result", length = 2000)
    private String textResult;

    @Column(length = 2000)
    private String conclusion;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    public Research(String name) {
        this.name = name;
    }

    public void applyResult(BigDecimal numberResult, String unit, String textResult, String conclusion) {
        this.numberResult = numberResult;
        this.unit = unit;
        this.textResult = textResult;
        this.conclusion = conclusion;
        this.registrationDate = LocalDateTime.now();
    }

    public boolean hasResult() {
        return conclusion != null && !conclusion.isBlank();
    }
}