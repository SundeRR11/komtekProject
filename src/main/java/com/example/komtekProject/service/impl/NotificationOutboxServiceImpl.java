package com.example.komtekProject.service.impl;

import com.example.komtekProject.entity.NotificationOutbox;
import com.example.komtekProject.enums.RecipientType;
import com.example.komtekProject.repository.NotificationOutboxRepository;
import com.example.komtekProject.service.NotificationOutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationOutboxServiceImpl implements NotificationOutboxService {

    private final NotificationOutboxRepository outboxRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFailedNotification(RecipientType recipientType,
                                       String recipientAddress,
                                       String messageText,
                                       String errorMessage,
                                       Long relatedEntityId) {
        NotificationOutbox outbox = new NotificationOutbox(
                recipientType, recipientAddress, messageText, errorMessage, relatedEntityId
        );
        NotificationOutbox saved = outboxRepository.save(outbox);
        log.info("Неудачное оповещение сохранено в outbox. ID: {}, Type: {}, Related ID: {}",
                saved.getId(), recipientType, relatedEntityId);
    }
}