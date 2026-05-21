package com.example.komtekProject.dto;

import com.example.komtekProject.enums.AttachmentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentRequestDto {

    @NotNull(message = "ID пациента обязателен")
    @Positive(message = "ID пациента должен быть положительным числом")
    private Long patientId;

    @NotNull(message = "ID МО обязателен")
    @Positive(message = "ID МО должен быть положительным числом")
    private Long moId;

    @NotNull(message = "Тип прикрепления обязателен")
    private AttachmentType type;
}