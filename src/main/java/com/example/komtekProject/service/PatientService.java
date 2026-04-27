package com.example.komtekProject.service;

import com.example.komtekProject.dto.InsurancePolicyResponseDto;
import com.example.komtekProject.entity.InsurancePolicy;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.exception.PatientNotFoundException;
import com.example.komtekProject.repository.InsurancePolicyRepository;
import com.example.komtekProject.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private InsurancePolicyRepository insurancePolicyRepository;

    public PatientService (PatientRepository patientRepository,
                           InsurancePolicyRepository insurancePolicyRepository) {
        this.patientRepository = patientRepository;
        this.insurancePolicyRepository = insurancePolicyRepository;
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    public InsurancePolicyResponseDto getPolicyByPatientId(Long patientId) {
        getPatientById(patientId);

        InsurancePolicy policy = insurancePolicyRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Полис не найден для пациента ID: " + patientId));
        return new InsurancePolicyResponseDto(
                policy.getId(),
                policy.getPatient().getId(),
                policy.getCreatedDate(),
                policy.getPolicyNumber()
        );
    }

    public InsurancePolicy addPolicyToPatient(Long patientId, String policyNumber){
        Patient patient = getPatientById(patientId);
        InsurancePolicy policy = new InsurancePolicy(patient, policyNumber);
        patient.setInsurancePolicy(policy);
        return insurancePolicyRepository.save(policy);
    }

}
