package com.example.komtekProject.annotation;


import com.example.komtekProject.annotation.validation.EnpValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnpValidator.class)
@Documented
public @interface ValidEnp {

    String message() default "Неверный формат ЕНП. Должен содержать 16 цифр";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String pattern() default "^\\d{16}$";
}
