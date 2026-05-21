package com.example.komtekProject.listener;

import com.example.komtekProject.enums.RecipientType;
import com.example.komtekProject.event.AttachmentCreatedEvent;
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
public class AttachmentNotificationListener {

    private final SftpNotificationService sftpService;
    private final NotificationMessageService messageService;
    private final NotificationOutboxService outboxService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAttachmentCreated(AttachmentCreatedEvent event) {
        log.info("Событие AttachmentCreated: attachmentId={}, patient={}, email={}",
                event.attachmentId(), event.patientFullName(), event.patientEmail());

        if (event.patientEmail() == null || event.patientEmail().isBlank()) {
            log.warn("У пациента '{}' нет email — оповещение пропущено (attachmentId={})",
                    event.patientFullName(), event.attachmentId());
            return;
        }

        String message = messageService.buildAttachmentMessage(
                event.patientFullName(),
                event.type(),
                event.moName(),
                event.registrationDate()
        );

        try {
            sftpService.uploadToSftp(event.patientEmail(), message, event.attachmentId());
            log.info("Оповещение пациента успешно отправлено на SFTP. Email: {}, Attachment ID: {}",
                    event.patientEmail(), event.attachmentId());
        } catch (Exception e) {
            log.error("Ошибка отправки на SFTP. Email: {}, Attachment ID: {}. Сохраняем в outbox.",
                    event.patientEmail(), event.attachmentId(), e);
            outboxService.saveFailedNotification(
                    RecipientType.PATIENT,
                    event.patientEmail(),
                    message,
                    e.getMessage(),
                    event.attachmentId()
            );
        }
    }
}