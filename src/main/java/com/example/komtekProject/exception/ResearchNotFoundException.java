package com.example.komtekProject.exception;

public class ResearchNotFoundException extends BusinessException {

    public ResearchNotFoundException(Long orderId, Long researchId) {
        super(ErrorCode.RESEARCH_NOT_FOUND.getCode(),
                String.format("У направления %d не найдено исследование с id %d", orderId, researchId));
    }
}