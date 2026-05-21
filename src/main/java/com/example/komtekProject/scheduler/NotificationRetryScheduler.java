package com.example.komtekProject.scheduler;

import com.example.komtekProject.config.NotificationRetryProperties;
import com.example.komtekProject.entity.NotificationOutbox;
import com.example.komtekProject.repository.NotificationOutboxRepository;
import com.example.komtekProject.service.SftpNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationRetryScheduler {

    private final NotificationOutboxRepository outboxRepository;
    private final SftpNotificationService sftpService;
    private final NotificationRetryProperties retryProperties;

    @Scheduled(fixedDelayString = "${notification.retry.scheduler-delay-ms}")
    public void retryFailedNotifications() {
        LocalDateTime cutoffTime = LocalDateTime.now()
                .minusMinutes(retryProperties.getMinIntervalMinutes());

        List<NotificationOutbox> failed = outboxRepository.findRetryable(
                cutoffTime,
                PageRequest.of(0, retryProperties.getBatchSize())
        );

        if (failed.isEmpty()) {
            return;
        }

        log.info("Переотправка оповещений: {} записей", failed.size());

        for (NotificationOutbox notification : failed) {
            processOne(notification);
        }
    }

    private void processOne(NotificationOutbox notification) {
        if (notification.getAttemptCount() >= retryProperties.getMaxAttempts()) {
            log.error("Превышен лимит попыток ({}) для оповещения ID: {} ({}). Удаляем из очереди.",
                    retryProperties.getMaxAttempts(), notification.getId(), notification.getRecipientType());
            outboxRepository.delete(notification);
            return;
        }

        try {
            sftpService.uploadToSftp(
                    notification.getRecipientAddress(),
                    notification.getMessageText(),
                    notification.getRelatedEntityId()
            );
            outboxRepository.delete(notification);
            log.info("Оповещение ID: {} ({}) переотправлено и удалено из очереди",
                    notification.getId(), notification.getRecipientType());

        } catch (Exception e) {
            log.warn("Повторная ошибка отправки оповещения ID: {} (попытка {}/{}): {}",
                    notification.getId(),
                    notification.getAttemptCount() + 1,
                    retryProperties.getMaxAttempts(),
                    e.getMessage());
            notification.registerFailedAttempt(e.getMessage());
            outboxRepository.save(notification);
        }
    }
}