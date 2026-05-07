package com.example.komtekProject.mapper;

import com.example.komtekProject.dto.OrderResponseDto;
import com.example.komtekProject.entity.Order;
import com.example.komtekProject.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient", target = "patientFullName", qualifiedByName = "fullName")
    @Mapping(source = "patient.snils", target = "patientSnils")
    @Mapping(source = "patient.insurancePolicy.policyNumber", target = "patientEnp")
    OrderResponseDto toDto(Order order);

    List<OrderResponseDto> toDtoList(List<Order> orders);

    @Named("fullName")
    default String getFullName(Patient patient) {
        if (patient == null) return null;
        return String.format("%s %s %s",
                patient.getLastName() != null ? patient.getLastName() : "",
                patient.getFirstName() != null ? patient.getFirstName() : "",
                patient.getMiddleName() != null ? patient.getMiddleName() : "").trim();
    }
}