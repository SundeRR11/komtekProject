package com.example.komtekProject.listener;

import com.example.komtekProject.enums.RecipientType;
import com.example.komtekProject.event.OrderCreatedEvent;
import com.example.komtekProject.event.OrderStatusChangedEvent;
import com.example.komtekProject.service.NotificationMessageService;
import com.example.komtekProject.service.NotificationOutboxService;
import com.example.komtekProject.service.SftpNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderNotificationListener {

    private final SftpNotificationService sftpService;
    private final NotificationMessageService messageService;
    private final NotificationOutboxService outboxService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Событие OrderCreated: orderId={}, recipient={}",
                event.orderId(), event.recipientAddress());

        String message = messageService.buildOrderCreationMessage(event.orderId());
        sendOrSaveToOutbox(event.recipientAddress(), message, event.orderId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStatusChanged(OrderStatusChangedEvent event) {
        log.info("Событие OrderStatusChanged: orderId={}, status={}, recipient={}",
                event.orderId(), event.newStatus(), event.recipientAddress());

        String message = messageService.buildOrderStatusChangeMessage(event.orderId(), event.newStatus());
        sendOrSaveToOutbox(event.recipientAddress(), message, event.orderId());
    }

    private void sendOrSaveToOutbox(String recipient, String message, Long orderId) {
        try {
            sftpService.uploadToSftp(recipient, message, orderId);
            log.info("Оповещение МО успешно отправлено на SFTP. Recipient: {}, Order ID: {}",
                    recipient, orderId);
        } catch (Exception e) {
            log.error("Ошибка отправки на SFTP. Recipient: {}, Order ID: {}. Сохраняем в outbox.",
                    recipient, orderId, e);
            outboxService.saveFailedNotification(
                    RecipientType.MO,
                    recipient,
                    message,
                    e.getMessage(),
                    orderId
            );
        }
    }
}