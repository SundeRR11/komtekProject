package com.example.komtekProject.exception;

import com.example.komtekProject.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePatientNotFound(PatientNotFoundException ex) {
        log.warn("Пациент не найден: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotFound(OrderNotFoundException ex) {
        log.warn("Заявка не найдена: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidOrderUpdateException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidUpdate(InvalidOrderUpdateException ex) {
        log.error("Ошибка обновления заявки: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MedicalOrganizationNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMedOrgNotFound(MedicalOrganizationNotFoundException ex) {
        log.warn("Медицинская организация не найдена: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidStatusTransition(InvalidStatusTransitionException ex) {
        log.warn("Невозможный переход статуса: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ResearchNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResearchNotFound(ResearchNotFoundException ex) {
        log.warn("Исследование не найдено: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidResearchResultException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidResearchResult(InvalidResearchResultException ex) {
        log.warn("Некорректный результат исследования: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException ex) {
        log.error("Бизнес-ошибка: {} - {}", ex.getCode(), ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error("Ошибка валидации: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorResponse.addError(
                        ErrorCode.VALIDATION_ERROR.getCode(),
                        String.format("Поле '%s': %s", fieldError.getField(),
                                fieldError.getDefaultMessage())
                )
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(
            ConstraintViolationException ex) {
        log.error("Ошибка валидации параметров: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();

        ex.getConstraintViolations().forEach(violation ->
                errorResponse.addError(
                        ErrorCode.VALIDATION_ERROR.getCode(),
                        violation.getMessage()
                )
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        log.error("Неверный тип параметра: {}", ex.getMessage());
        String message = String.format("Параметр '%s' должен быть типа %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ErrorCode.TYPE_MISMATCH.getCode(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleNotReadable(
            HttpMessageNotReadableException ex) {
        log.error("Неверный формат JSON: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ErrorCode.INVALID_JSON.getCode(), "Неверный формат JSON");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("Неверный аргумент: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ErrorCode.VALIDATION_ERROR.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        log.error("Внутренняя ошибка сервера", ex);
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(
                ErrorCode.INTERNAL_ERROR.getCode(),
                "Внутренняя ошибка сервера"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}