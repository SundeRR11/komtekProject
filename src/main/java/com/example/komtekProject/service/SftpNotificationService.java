package com.example.komtekProject.service;

public interface SftpNotificationService {

    void uploadToSftp(String recipientAddress, String messageText, Long orderId) throws Exception;
}