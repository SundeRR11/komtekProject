package com.example.komtekProject.event;

import com.example.komtekProject.enums.AttachmentType;

import java.time.LocalDate;

public record AttachmentCreatedEvent(
        Long attachmentId,
        String patientEmail,
        String patientFullName,
        AttachmentType type,
        String moName,
        LocalDate registrationDate
) {}