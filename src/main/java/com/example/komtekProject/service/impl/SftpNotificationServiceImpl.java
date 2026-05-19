package com.example.komtekProject.service.impl;

import com.example.komtekProject.config.SftpProperties;
import com.example.komtekProject.service.NotificationOutboxService;
import com.example.komtekProject.service.SftpNotificationService;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class SftpNotificationServiceImpl implements SftpNotificationService {

    private final SftpProperties sftpProperties;
    private final NotificationOutboxService outboxService;  // ← Новый сервис вместо repository

    @PostConstruct
    public void logConfig() {
        log.info("SFTP конфиг: host={}, port={}, user={}, remoteDir={}",
                sftpProperties.getHost(),
                sftpProperties.getPort(),
                sftpProperties.getUsername(),
                sftpProperties.getRemoteDir());
    }

    @Override
    public void sendNotification(String recipientAddress, String messageText, Long orderId) {
        log.info("Начало отправки оповещения. Recipient: {}, Order ID: {}", recipientAddress, orderId);
        try {
            uploadToSftp(recipientAddress, messageText, orderId);
            log.info("Оповещение успешно отправлено на SFTP. Recipient: {}, Order ID: {}",
                    recipientAddress, orderId);
        } catch (Exception e) {
            log.error("Ошибка отправки на SFTP. Recipient: {}, Order ID: {}. Сохраняем в outbox. Причина: {}",
                    recipientAddress, orderId, e.getMessage());
            // ✅ Вызов через другой бин — Spring AOP сработает
            outboxService.saveFailedNotification(recipientAddress, messageText, e.getMessage(), orderId);
        }
    }

    @Override
    public void uploadToSftp(String recipientAddress, String messageText, Long orderId) throws Exception {
        log.debug("Подключение к SFTP {}:{}", sftpProperties.getHost(), sftpProperties.getPort());

        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(
                    sftpProperties.getUsername(),
                    sftpProperties.getHost(),
                    sftpProperties.getPort()
            );
            session.setPassword(sftpProperties.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(sftpProperties.getConnectTimeoutMs());
            log.debug("SSH сессия установлена");

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect(sftpProperties.getConnectTimeoutMs());
            log.debug("SFTP канал открыт");

            String fileName = generateFileName(recipientAddress, orderId);
            String remotePath = sftpProperties.getRemoteDir() + "/" + fileName;

            String fileContent = String.format(
                    "To: %s%nDate: %s%nOrder ID: %d%n%n%s",
                    recipientAddress,
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    orderId,
                    messageText
            );

            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(
                    fileContent.getBytes(StandardCharsets.UTF_8))) {
                channelSftp.put(inputStream, remotePath);
            }

            log.info("Файл '{}' загружен на SFTP по пути '{}'", fileName, remotePath);

        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    private String generateFileName(String recipientAddress, Long orderId) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        String sanitized = recipientAddress.replaceAll("[^a-zA-Z0-9]", "_");
        return String.format("notification_%s_%d_%s.txt", sanitized, orderId, timestamp);
    }
}