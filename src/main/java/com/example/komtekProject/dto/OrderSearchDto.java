package com.example.komtekProject.dto;


import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchDto {

    @Positive(message = "ID должен быть положительным числом")
    private Long id;

    @Size(max = 200, message = "ФИО не может превышать 200 символов")
    private String patientFullName;

    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate patientBirthDate;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3} \\d{2}$", message = "СНИЛС должен быть в формате 123-456-789 01")
    private String patientSnils;

    @Pattern(regexp = "^\\d{16}$", message = "ЕНП должен состоять из 16 цифр")
    private String patientEnp;

    @Pattern(regexp = "^(REGISTERED|IN_PROGRESS|COMPLETED|CANCELED)$",
            message = "Статус должен быть: REGISTERED, IN_PROGRESS, COMPLETED, CANCELED")
    private String status;

}
