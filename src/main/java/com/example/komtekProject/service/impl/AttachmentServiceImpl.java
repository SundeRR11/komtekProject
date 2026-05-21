package com.example.komtekProject.service.impl;

import com.example.komtekProject.dto.AttachmentRequestDto;
import com.example.komtekProject.dto.AttachmentResponseDto;
import com.example.komtekProject.entity.Attachment;
import com.example.komtekProject.entity.MedicalOrganization;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.event.AttachmentCreatedEvent;
import com.example.komtekProject.exception.MedicalOrganizationNotFoundException;
import com.example.komtekProject.exception.PatientNotFoundException;
import com.example.komtekProject.mapper.AttachmentMapper;
import com.example.komtekProject.repository.AttachmentRepository;
import com.example.komtekProject.repository.MedicalOrganizationRepository;
import com.example.komtekProject.repository.PatientRepository;
import com.example.komtekProject.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final PatientRepository patientRepository;
    private final MedicalOrganizationRepository medOrgRepository;
    private final AttachmentMapper attachmentMapper;
    private final ApplicationEventPublisher eventPublisher;  // ← добавили

    @Override
    @Transactional
    public AttachmentResponseDto createAttachment(AttachmentRequestDto request) {
        log.debug("Создание прикрепления для пациента ID: {}, МО ID: {}, тип: {}",
                request.getPatientId(), request.getMoId(), request.getType());

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> {
                    log.warn("Пациент с ID {} не найден", request.getPatientId());
                    return new PatientNotFoundException(request.getPatientId());
                });

        MedicalOrganization mo = medOrgRepository.findById(request.getMoId())
                .orElseThrow(() -> {
                    log.warn("МО с ID {} не найдена", request.getMoId());
                    return new MedicalOrganizationNotFoundException(request.getMoId());
                });

        Attachment attachment = new Attachment(patient, mo, request.getType());
        Attachment saved = attachmentRepository.save(attachment);

        log.debug("Прикрепление создано. ID: {}, пациент: {}, МО: {}",
                saved.getId(), patient.getId(), mo.getName());

        eventPublisher.publishEvent(new AttachmentCreatedEvent(
                saved.getId(),
                patient.getEmail(),
                buildFullName(patient),
                saved.getType(),
                mo.getName(),
                saved.getCreatedDate().toLocalDate()
        ));

        return attachmentMapper.toDto(saved);
    }

    private String buildFullName(Patient patient) {
        return String.format("%s %s %s",
                patient.getLastName() != null ? patient.getLastName() : "",
                patient.getFirstName() != null ? patient.getFirstName() : "",
                patient.getMiddleName() != null ? patient.getMiddleName() : "").trim();
    }
}