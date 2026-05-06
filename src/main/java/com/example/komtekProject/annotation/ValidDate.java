package com.example.komtekProject.annotation;

import com.example.komtekProject.annotation.validation.DateValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface ValidDate {

    String message() default "Неверный формат даты. Используйте yyyy-MM-dd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String pattern() default "^\\d{4}-\\d{2}-\\d{2}$";

    boolean future() default false;

    boolean past() default false;
}