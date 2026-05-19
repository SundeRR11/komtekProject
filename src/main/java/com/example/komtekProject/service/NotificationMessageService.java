package com.example.komtekProject.service;

import com.example.komtekProject.enums.OrderStatus;

public interface NotificationMessageService {

    String buildCreationMessage(Long orderId);

    String buildStatusChangeMessage(Long orderId, OrderStatus newStatus);
}