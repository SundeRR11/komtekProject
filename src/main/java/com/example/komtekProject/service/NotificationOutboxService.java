package com.example.komtekProject.service;

import com.example.komtekProject.enums.RecipientType;

public interface NotificationOutboxService {

    void saveFailedNotification(
            RecipientType recipientType,
            String recipientAddress,
            String messageText,
            String errorMessage,
            Long relatedEntityId);

}