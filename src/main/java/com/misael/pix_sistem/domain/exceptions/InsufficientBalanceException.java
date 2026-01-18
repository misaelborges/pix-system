package com.misael.pix_sistem.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InsufficientBalanceException extends BusinessException {

    public InsufficientBalanceException(String message) {
        super(message);
    }

    public InsufficientBalanceException(String message, BigDecimal currentBalance, BigDecimal requestedAmount) {
        super("Saldo insuficiente. Saldo atual: R$ " + currentBalance + ", valor solicitado: R$ " + requestedAmount);
    }
}
