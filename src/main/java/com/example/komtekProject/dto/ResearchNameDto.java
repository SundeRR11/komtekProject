package com.example.komtekProject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResearchNameDto {

    @NotBlank(message = "Название исследования обязательно")
    @Size(max = 255, message = "Название не может превышать 255 символов")
    private String name;
}