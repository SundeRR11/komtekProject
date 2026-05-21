package com.example.komtekProject.service;

import com.example.komtekProject.dto.ResearchListResponseDto;
import com.example.komtekProject.dto.ResearchResultUploadDto;

public interface ResearchService {

    ResearchListResponseDto getResearchesByOrderId(Long orderId);

    ResearchListResponseDto uploadResults(Long orderId, ResearchResultUploadDto uploadDto);
}