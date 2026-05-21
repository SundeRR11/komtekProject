package com.example.komtekProject.controller;

import com.example.komtekProject.dto.AttachmentRequestDto;
import com.example.komtekProject.dto.AttachmentResponseDto;
import com.example.komtekProject.service.AttachmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/attachments")
@RequiredArgsConstructor
@Validated
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping
    public ResponseEntity<AttachmentResponseDto> createAttachment(
            @Valid @RequestBody AttachmentRequestDto request) {

        log.info("Создание прикрепления для пациента ID: {}, МО ID: {}",
                request.getPatientId(), request.getMoId());

        AttachmentResponseDto attachment = attachmentService.createAttachment(request);

        log.info("Прикрепление создано: ID={}", attachment.getId());
        return ResponseEntity.ok(attachment);
    }
}