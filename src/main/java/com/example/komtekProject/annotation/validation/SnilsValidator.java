package com.example.komtekProject.annotation.validation;

import com.example.komtekProject.annotation.ValidSnils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SnilsValidator implements ConstraintValidator<ValidSnils, String> {

    private String pattern;
    private boolean checkSum;

    @Override
    public void initialize(ValidSnils constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
        this.checkSum = constraintAnnotation.checkSum();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        if (!value.matches(pattern)) {
            return false;
        }

        if (checkSum) {
            return isValidSnilsChecksum(value);
        }

        return true;
    }

    private boolean isValidSnilsChecksum(String snils) {
        String numbers = snils.replaceAll("\\D", "");
        if (numbers.length() != 11) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(numbers.charAt(i));
            sum += digit * (9 - i);
        }

        int checkDigit = Integer.parseInt(numbers.substring(9));
        int calculatedCheck;

        if (sum < 100) {
            calculatedCheck = sum;
        } else if (sum == 100 || sum == 101) {
            calculatedCheck = 0;
        } else {
            calculatedCheck = sum % 101;
            if (calculatedCheck == 100) {
                calculatedCheck = 0;
            }
        }

        return checkDigit == calculatedCheck;
    }
}