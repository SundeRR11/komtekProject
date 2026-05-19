package com.example.komtekProject.service.impl;

import com.example.komtekProject.enums.OrderStatus;
import com.example.komtekProject.service.NotificationMessageService;
import org.springframework.stereotype.Service;

@Service
public class NotificationMessageServiceImpl implements NotificationMessageService {

    @Override
    public String buildCreationMessage(Long orderId) {
        return String.format(
                "Вы указаны исполнителем в новом направлении. " +
                        "Получить информацию о нем можно по идентификатору: %d",
                orderId
        );
    }

    @Override
    public String buildStatusChangeMessage(Long orderId, OrderStatus newStatus) {
        return String.format(
                "Статус направления с id %d изменен на %s. " +
                        "Для получения подробностей запросите его данные или свяжитесь с исполнителем.",
                orderId, newStatus
        );
    }
}