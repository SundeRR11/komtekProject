package com.example.komtekProject.mapper;

import com.example.komtekProject.dto.AttachmentResponseDto;
import com.example.komtekProject.entity.Attachment;
import com.example.komtekProject.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient", target = "patientFullName", qualifiedByName = "fullName")
    @Mapping(source = "medicalOrganization.id", target = "moId")
    @Mapping(source = "medicalOrganization.name", target = "moName")
    AttachmentResponseDto toDto(Attachment attachment);

    @Named("fullName")
    default String getFullName(Patient patient) {
        if (patient == null) return null;
        return String.format("%s %s %s",
                patient.getLastName() != null ? patient.getLastName() : "",
                patient.getFirstName() != null ? patient.getFirstName() : "",
                patient.getMiddleName() != null ? patient.getMiddleName() : "").trim();
    }
}