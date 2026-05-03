package com.example.komtekProject.service;


import com.example.komtekProject.dto.PatientResponseDto;

public interface PatientService {

    PatientResponseDto getPatientById(Long id);
}
