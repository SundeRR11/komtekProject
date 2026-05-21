package com.example.komtekProject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResearchResultDto {

    @NotNull(message = "ID исследования обязателен")
    @Positive(message = "ID исследования должен быть положительным числом")
    private Long id;

    private BigDecimal numberResult;

    @Size(max = 50, message = "Единица измерения не может превышать 50 символов")
    private String unit;

    @Size(max = 2000, message = "Текстовый результат не может превышать 2000 символов")
    private String textResult;

    @NotBlank(message = "Заключение обязательно")
    @Size(max = 2000, message = "Заключение не может превышать 2000 символов")
    private String conclusion;
}