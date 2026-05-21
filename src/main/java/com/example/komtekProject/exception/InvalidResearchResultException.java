package com.example.komtekProject.exception;

public class InvalidResearchResultException extends BusinessException {

    public InvalidResearchResultException(String message) {
        super(ErrorCode.INVALID_RESEARCH_RESULT.getCode(), message);
    }
}