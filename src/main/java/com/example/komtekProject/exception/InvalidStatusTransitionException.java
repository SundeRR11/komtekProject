package com.example.komtekProject.exception;

import com.example.komtekProject.enums.OrderStatus;

public class InvalidStatusTransitionException extends BusinessException {

    public InvalidStatusTransitionException(OrderStatus from, OrderStatus to) {
        super(ErrorCode.INVALID_STATUS_TRANSITION.getCode(),
                String.format("Невозможно перевести направление в статусе %s в статус %s", from, to));
    }
}