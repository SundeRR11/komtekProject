package com.example.komtekProject.annotation.validation;

import com.example.komtekProject.annotation.ValidDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

    private String pattern;
    private boolean future;
    private boolean past;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
        this.future = constraintAnnotation.future();
        this.past = constraintAnnotation.past();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        if (!value.matches(pattern)) {
            return false;
        }

        try {
            LocalDate date = LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);

            if (past && !date.isBefore(LocalDate.now())) {
                return false;
            }

            if (future && !date.isAfter(LocalDate.now())) {
                return false;
            }

        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
