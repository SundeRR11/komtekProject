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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Ошибка валидации: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorResponse.addError("VALIDATION_ERROR", fieldError.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex) {
        log.error("Ошибка валидации параметров: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorResponse.addError("VALIDATION_ERROR", violation.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException ex) {
        log.error("Бизнес-ошибка: {} - {}", ex.getCode(), ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("Неверный тип параметра: {}", ex.getMessage());
        String message = String.format("Параметр '%s' должен быть типа %s",
                ex.getName(), ex.getRequiredType().getSimpleName());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError("TYPE_MISMATCH", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleNotReadable(HttpMessageNotReadableException ex) {
        log.error("Неверный формат JSON: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError("INVALID_JSON", "Неверный формат JSON");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        log.error("Внутренняя ошибка сервера: {}", ex.getMessage(), ex);
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError("INTERNAL_ERROR", "Внутренняя ошибка сервера");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidOrderUpdateException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidUpdate(InvalidOrderUpdateException ex) {
        log.error("Ошибка обновления заявки: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.addError(ErrorCode.INVALID_UPDATE.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}