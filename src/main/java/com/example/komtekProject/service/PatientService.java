package com.example.komtekProject.service;

import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.repository.PatientRepository;
import com.sun.security.auth.PrincipalComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
