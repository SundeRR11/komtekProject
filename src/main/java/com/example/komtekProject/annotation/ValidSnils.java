package com.example.komtekProject.annotation;

import com.example.komtekProject.annotation.validation.SnilsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SnilsValidator.class)
@Documented
public @interface ValidSnils {

    String message() default "Неверный формат СНИЛС. Используйте 123-456-789 01";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String pattern() default "^\\d{3}-\\d{3}-\\d{3} \\d{2}$";

    boolean checkSum() default true;
}
