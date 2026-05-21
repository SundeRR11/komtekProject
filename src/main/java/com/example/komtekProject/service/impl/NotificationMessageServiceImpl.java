package com.example.komtekProject.service.impl;

import com.example.komtekProject.enums.AttachmentType;
import com.example.komtekProject.enums.OrderStatus;
import com.example.komtekProject.service.NotificationMessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class NotificationMessageServiceImpl implements NotificationMessageService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public String buildOrderCreationMessage(Long orderId) {
        return String.format(
                "Вы указаны исполнителем в новом направлении. " +
                        "Получить информацию о нем можно по идентификатору: %d",
                orderId
        );
    }

    @Override
    public String buildOrderStatusChangeMessage(Long orderId, OrderStatus newStatus) {
        return String.format(
                "Статус направления с id %d изменен на %s. " +
                        "Для получения подробностей запросите его данные или свяжитесь с исполнителем.",
                orderId, newStatus
        );
    }

    @Override
    public String buildAttachmentMessage(String patientFullName,
                                         AttachmentType type,
                                         String moName,
                                         LocalDate registrationDate) {
        return String.format(
                "Уважаемый %s, для вас зарегистрировано новое прикрепление к медицинской организации.%n" +
                        "Тип прикрепления: %s%n" +
                        "Медицинская организация: %s%n" +
                        "Дата регистрации: %s%n" +
                        "Если вы обнаружили неточность в данных, просьба обратиться в вашу поликлинику.",
                patientFullName,
                translateType(type),
                moName,
                registrationDate.format(DATE_FORMATTER)
        );
    }

    private String translateType(AttachmentType type) {
        return switch (type) {
            case AMBULATORY -> "амбулаторное";
            case DENTAL -> "стоматологическое";
        };
    }
}