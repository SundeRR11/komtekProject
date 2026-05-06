package com.example.komtekProject.service.impl;

import com.example.komtekProject.dto.PatientResponseDto;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.exception.PatientNotFoundException;
import com.example.komtekProject.mapper.PatientMapper;
import com.example.komtekProject.repository.PatientRepository;
import com.example.komtekProject.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientResponseDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        return patientMapper.toDto(patient);
    }

}
