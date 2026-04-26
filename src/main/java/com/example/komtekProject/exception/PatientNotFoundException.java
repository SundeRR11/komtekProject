package com.example.komtekProject.exception;

public class PatientNotFoundException extends BusinessException {

    public PatientNotFoundException(Long id) {
        super(ErrorCode.PATIENT_NOT_FOUND, "Пациент с ID " + id + " не найден");
    }

    public PatientNotFoundException(String snils) {
        super(ErrorCode.PATIENT_NOT_FOUND, "Пациент со СНИЛС " + snils + " не найден");
    }
}