package com.misael.pix_sistem.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class MaxPixKeysLimitException extends InvalidOperationException{
    public MaxPixKeysLimitException() {
        super("Já existem 5 chaves cadastradas na sua conta");
    }
}
