package com.example.komtekProject.exception;

public class PatientNotFoundException extends BusinessException {

    public PatientNotFoundException(Long id) {
        super("PATIENT_NOT_FOUND", "Пациент с ID " + id + " не найден");
    }
}