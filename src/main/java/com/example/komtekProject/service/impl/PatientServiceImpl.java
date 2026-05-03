package com.example.komtekProject.service.impl;

import com.example.komtekProject.dto.PatientResponseDto;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.exception.PatientNotFoundException;
import com.example.komtekProject.repository.PatientRepository;
import com.example.komtekProject.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientResponseDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        return new PatientResponseDto(
                patient.getId(),
                patient.getLastName(),
                patient.getFirstName(),
                patient.getMiddleName(),
                patient.getBirthDate(),
                patient.getGender(),
                patient.getSnils()
        );
    }

}
