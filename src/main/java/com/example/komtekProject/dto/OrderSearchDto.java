package com.example.komtekProject.dto;


import com.example.komtekProject.annotation.ValidDate;
import com.example.komtekProject.annotation.ValidEnp;
import com.example.komtekProject.annotation.ValidSnils;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSearchDto {

    @Positive(message = "ID должен быть положительным числом")
    private Long id;

    @Size(max = 200, message = "ФИО не может превышать 200 символов")
    private String patientFullName;

    @ValidDate
    private LocalDate patientBirthDate;

    @ValidSnils
    private String patientSnils;

    @ValidEnp
    private String patientEnp;

    @Pattern(regexp = "^(REGISTERED|IN_PROGRESS|COMPLETED|CANCELED)$",
            message = "Статус должен быть: REGISTERED, IN_PROGRESS, COMPLETED, CANCELED")
    private String status;

}
