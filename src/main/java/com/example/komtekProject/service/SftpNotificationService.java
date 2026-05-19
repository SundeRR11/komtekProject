package com.example.komtekProject.service;

public interface SftpNotificationService {

    void sendNotification(String recipientAddress, String messageText, Long orderId);

    void uploadToSftp(String recipientAddress, String messageText, Long orderId) throws Exception;
}