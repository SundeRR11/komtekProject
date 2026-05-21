package com.example.komtekProject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    PATIENT_NOT_FOUND("PATIENT_NOT_FOUND", "Пациент не найден"),
    ORDER_NOT_FOUND("ORDER_NOT_FOUND", "Заявка не найдена"),
    MED_ORG_NOT_FOUND("MED_ORG_NOT_FOUND", "Медицинская организация не найдена"),
    RESEARCH_NOT_FOUND("RESEARCH_NOT_FOUND", "Исследование не найдено"),
    INVALID_STATUS_TRANSITION("INVALID_STATUS_TRANSITION", "Невозможный переход статуса"),
    INVALID_RESEARCH_RESULT("INVALID_RESEARCH_RESULT", "Некорректный результат исследования"),
    VALIDATION_ERROR("VALIDATION_ERROR", "Ошибка валидации"),
    TYPE_MISMATCH("TYPE_MISMATCH", "Неверный тип параметра"),
    INVALID_JSON("INVALID_JSON", "Неверный формат JSON"),
    DUPLICATE_KEY("DUPLICATE_KEY", "Запись с такими данными уже существует"),
    INTERNAL_ERROR("INTERNAL_ERROR", "Внутренняя ошибка сервера"),
    INVALID_UPDATE("INVALID_UPDATE", "Неверный запрос на обновление");

    private final String code;
    private final String defaultMessage;
}