package com.example.komtekProject.service;

import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService (PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findPatientById(id);
    }
}
