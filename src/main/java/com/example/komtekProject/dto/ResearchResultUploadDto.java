package com.example.komtekProject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResearchResultUploadDto {

    @NotEmpty(message = "Список результатов не может быть пустым")
    @Valid
    private List<ResearchResultDto> researches;
}