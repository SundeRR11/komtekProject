package com.example.komtekProject.exception;

public class OrderNotFoundException extends BusinessException {

    public OrderNotFoundException(Long id) {
        super(ErrorCode.ORDER_NOT_FOUND, "Заявка с ID " + id + " не найдена");
    }
}