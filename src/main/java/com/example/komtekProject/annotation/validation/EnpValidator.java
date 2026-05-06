package com.example.komtekProject.annotation.validation;

import com.example.komtekProject.annotation.ValidEnp;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnpValidator implements ConstraintValidator<ValidEnp, String> {

    private String pattern;

    @Override
    public void initialize(ValidEnp constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.matches(pattern);
    }
}
