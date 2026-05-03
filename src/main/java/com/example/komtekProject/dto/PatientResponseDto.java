package com.example.komtekProject.dto;

import com.example.komtekProject.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PatientResponseDto {
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private Gender gender;
    private String snils;

}