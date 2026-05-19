package com.example.komtekProject.event;

import com.example.komtekProject.enums.OrderStatus;

public record OrderStatusChangedEvent(
        Long orderId,
        OrderStatus newStatus,
        String recipientAddress
) {}