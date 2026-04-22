package com.example.komtekProject.service;

import com.example.komtekProject.entity.InsurancePolicy;
import com.example.komtekProject.entity.Patient;
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
        return patientRepository.findPatientById(id);
    }

    public InsurancePolicy getPolicyByPatientId(Long patientId){
        return insurancePolicyRepository.findByPatientId(patientId);
    }

    public InsurancePolicy addPolicyToPatient(Long patientId, String policyNumber){
        Patient patient = getPatientById(patientId);
        InsurancePolicy policy = new InsurancePolicy(patient, policyNumber);
        patient.setInsurancePolicy(policy);
        return insurancePolicyRepository.save(policy);
    }

}
