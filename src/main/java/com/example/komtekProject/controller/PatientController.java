package com.example.komtekProject.controller;

import com.example.komtekProject.entity.InsurancePolicy;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping("/{id}/policy")
    public ResponseEntity<InsurancePolicy> getPolicyByPatient(@PathVariable Long id){
        InsurancePolicy policy = patientService.getPolicyByPatientId(id);
        return ResponseEntity.ok(policy);
    }

    @PostMapping("/{id}/policy")
    public ResponseEntity<InsurancePolicy> addPolicyToPatient(@PathVariable Long id,
                                                              @RequestParam String policyNumber){
        InsurancePolicy policy = patientService.addPolicyToPatient(id, policyNumber);
        return ResponseEntity.ok(policy);
    }
}
