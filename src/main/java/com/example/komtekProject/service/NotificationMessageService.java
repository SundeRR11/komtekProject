package com.example.komtekProject.service;

import com.example.komtekProject.enums.AttachmentType;
import com.example.komtekProject.enums.OrderStatus;

import java.time.LocalDate;

public interface NotificationMessageService {

    String buildOrderCreationMessage(Long orderId);

    String buildOrderStatusChangeMessage(Long orderId, OrderStatus newStatus);

    String buildAttachmentMessage(String patientFullName,
                                  AttachmentType type,
                                  String moName,
                                  LocalDate registrationDate);
}