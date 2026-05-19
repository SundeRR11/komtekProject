package com.example.komtekProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notification_outbox")
public class NotificationOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_address", nullable = false)
    private String recipientAddress;

    @Column(name = "message_text", nullable = false, length = 1000)
    private String messageText;

    @Column(name = "error_message", length = 2000)
    private String errorMessage;

    @Column(name = "last_attempt_time", nullable = false)
    private LocalDateTime lastAttemptTime;

    @Column(name = "attempt_count", nullable = false)
    private Integer attemptCount = 0;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    public NotificationOutbox(String recipientAddress, String messageText,
                              String errorMessage, Long orderId) {
        this.recipientAddress = recipientAddress;
        this.messageText = messageText;
        this.errorMessage = errorMessage;
        this.orderId = orderId;
        this.lastAttemptTime = LocalDateTime.now();
        this.createdDate = LocalDateTime.now();
        this.attemptCount = 1;
    }

    public void registerFailedAttempt(String errorMessage) {
        this.errorMessage = errorMessage;
        this.lastAttemptTime = LocalDateTime.now();
        this.attemptCount++;
    }
}