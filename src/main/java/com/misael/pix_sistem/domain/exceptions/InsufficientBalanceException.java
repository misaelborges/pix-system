package com.misael.pix_sistem.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InsufficientBalanceException extends BusinessException {

    public InsufficientBalanceException() {
        super("Saldo insuficiente para realizar a operação");
    }
}
