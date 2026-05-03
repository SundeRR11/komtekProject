package com.example.komtekProject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private List<ErrorDetailDto> errors = new ArrayList<>();


    public void addError(String code, String message) {
        this.errors.add(new ErrorDetailDto(code, message));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetailDto {
        private String code;
        private String message;
    }
}
