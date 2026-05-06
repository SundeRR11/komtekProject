package com.example.komtekProject.mapper;

import com.example.komtekProject.dto.PatientResponseDto;
import com.example.komtekProject.entity.Patient;


public interface PatientMapper {

    PatientResponseDto toDto(Patient patient);
}
