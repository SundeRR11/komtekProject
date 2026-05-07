package com.example.komtekProject.controller;

import com.example.komtekProject.dto.PatientResponseDto;
import com.example.komtekProject.service.impl.PatientServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServiceImpl patientService;

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
        log.info("Получение пациента по ID: {}", id);
        PatientResponseDto patient = patientService.getPatientById(id);
        log.info("Пациент найден. ID: {}, ФИО: {}", patient.getId(), patient.getFullName());
        return ResponseEntity.ok(patient);
    }

}
