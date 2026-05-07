package com.example.komtekProject.exception;

public class InvalidOrderUpdateException extends BusinessException {

    public InvalidOrderUpdateException(String message) {
        super(ErrorCode.INVALID_UPDATE.getCode(), message);
    }
}