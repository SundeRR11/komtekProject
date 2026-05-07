package com.example.komtekProject.dto;

import com.example.komtekProject.annotation.ValidDate;
import com.example.komtekProject.annotation.ValidSnils;
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

    @ValidDate
    private LocalDate birthDate;

    private Gender gender;

    @ValidSnils
    private String snils;

    public String getFullName() {
        return String.format("%s %s %s",
                lastName != null ? lastName : "",
                firstName != null ? firstName : "",
                middleName != null ? middleName : "").trim();
    }

}