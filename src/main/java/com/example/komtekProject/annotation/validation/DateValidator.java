package com.example.komtekProject.annotation.validation;

import com.example.komtekProject.annotation.ValidDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<ValidDate, LocalDate> {

    private boolean future;
    private boolean past;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        this.future = constraintAnnotation.future();
        this.past = constraintAnnotation.past();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        LocalDate now = LocalDate.now();

        if (past && !value.isBefore(now)) {
            return false;
        }

        if (future && !value.isAfter(now)) {
            return false;
        }

        return true;
    }
}