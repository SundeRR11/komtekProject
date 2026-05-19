package com.example.komtekProject.service;

public interface NotificationOutboxService {

    void saveFailedNotification(String recipientAddress, String messageText,
                                String errorMessage, Long orderId);
}