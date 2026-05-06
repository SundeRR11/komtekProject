package com.example.komtekProject.mapper.Impl;

import com.example.komtekProject.dto.PatientResponseDto;
import com.example.komtekProject.entity.Patient;
import com.example.komtekProject.mapper.PatientMapper;
import org.springframework.stereotype.Component;

@Component
public class PatientMapperImpl implements PatientMapper {

    @Override
    public PatientResponseDto toDto(Patient patient) {
        if (patient == null) {
            return null;
        }

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