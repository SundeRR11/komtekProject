package com.example.komtekProject.listener;

import com.example.komtekProject.event.OrderCreatedEvent;
import com.example.komtekProject.event.OrderStatusChangedEvent;
import com.example.komtekProject.service.NotificationMessageService;
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

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Событие OrderCreated: orderId={}, recipient={}",
                event.orderId(), event.recipientAddress());

        String message = messageService.buildCreationMessage(event.orderId());
        sftpService.sendNotification(event.recipientAddress(), message, event.orderId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStatusChanged(OrderStatusChangedEvent event) {
        log.info("Событие OrderStatusChanged: orderId={}, status={}, recipient={}",
                event.orderId(), event.newStatus(), event.recipientAddress());

        String message = messageService.buildStatusChangeMessage(event.orderId(), event.newStatus());
        sftpService.sendNotification(event.recipientAddress(), message, event.orderId());
    }
}