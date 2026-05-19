package com.example.komtekProject.event;

public record OrderCreatedEvent(
        Long orderId,
        String recipientAddress
) {}