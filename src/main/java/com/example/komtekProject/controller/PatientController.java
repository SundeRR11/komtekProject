package com.example.komtekProject.controller;

import com.example.komtekProject.dto.PatientResponseDto;
import com.example.komtekProject.service.impl.PatientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServiceImpl patientServiceImpl;

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientServiceImpl.getPatientById(id));
    }

}
