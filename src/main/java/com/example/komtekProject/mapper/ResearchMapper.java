package com.example.komtekProject.mapper;

import com.example.komtekProject.dto.ResearchResponseDto;
import com.example.komtekProject.entity.Research;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResearchMapper {

    ResearchResponseDto toDto(Research research);

    List<ResearchResponseDto> toDtoList(List<Research> researches);
}