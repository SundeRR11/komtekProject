package com.example.komtekProject.service.impl;

import com.example.komtekProject.dto.PatientResponseDto;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.exception.PatientNotFoundException;
import com.example.komtekProject.mapper.PatientMapper;
import com.example.komtekProject.repository.PatientRepository;
import com.example.komtekProject.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDto getPatientById(Long id) {
        log.debug("Поиск пациента по ID: {}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Пациент с ID {} не найден", id);
                    return new PatientNotFoundException(id);
                });

        log.debug("Пациент найден: ID={}, фамилия={}, имя={}", id, patient.getLastName(), patient.getFirstName());

        return patientMapper.toDto(patient);
    }
}