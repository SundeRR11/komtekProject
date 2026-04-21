package com.example.komtekProject.service;

import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient getPatientById(Long id) {
        return patientRepository.findPatientById(id);
    }
}
