package com.example.komtekProject.exception;

public class MedicalOrganizationNotFoundException extends BusinessException {

    public MedicalOrganizationNotFoundException(Long id) {
        super("MED_ORG_NOT_FOUND", "Медицинская организация с ID " + id + " не найдена");
    }
}