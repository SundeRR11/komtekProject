package com.example.komtekProject.dto;

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
public class OrderUpdateDto {

    @Positive(message = "ID заявки должен быть положительным числом")
    private Long id;

    @Size(max = 500, message = "Комментарий не может превышать 500 символов")
    private String comment;
}