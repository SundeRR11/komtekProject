package com.example.komtekProject.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {

    PATIENT_NOT_FOUND("PATIENT_NOT_FOUND", "Пациент не найден"),
    ORDER_NOT_FOUND("ORDER_NOT_FOUND", "Заявка не найдена"),
    VALIDATION_ERROR("VALIDATION_ERROR", "Ошибка валидации"),
    TYPE_MISMATCH("TYPE_MISMATCH", "Неверный тип параметра"),
    INVALID_JSON("INVALID_JSON", "Неверный формат JSON"),
    DUPLICATE_KEY("DUPLICATE_KEY", "Запись с такими данными уже существует"),
    INTERNAL_ERROR("INTERNAL_ERROR", "Внутренняя ошибка сервера");

    private final String code;
    private final String defaultMessage;

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
