package com.example.komtekProject.dto;

import com.example.komtekProject.enums.AttachmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponseDto {
    private Long id;
    private Long patientId;
    private String patientFullName;
    private Long moId;
    private String moName;
    private AttachmentType type;
    private LocalDateTime createdDate;
}