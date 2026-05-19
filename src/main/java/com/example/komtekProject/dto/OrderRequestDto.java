package com.example.komtekProject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    @NotNull(message = "ID пациента обязателен")
    @Positive(message = "ID пациента должен быть положительным числом")
    private Long patientId;

    @NotNull(message = "ID МО-создателя обязателен")
    @Positive(message = "ID МО-создателя должен быть положительным числом")
    private Long creatorOrgId;

    @NotNull(message = "ID МО-исполнителя обязателен")
    @Positive(message = "ID МО-исполнителя должен быть положительным числом")
    private Long executorOrgId;

    @Size(max = 500, message = "Комментарий не может превышать 500 символов")
    private String comment;
}