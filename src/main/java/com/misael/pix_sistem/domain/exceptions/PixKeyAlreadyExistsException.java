package com.misael.pix_sistem.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PixKeyAlreadyExistsException extends BusinessException {

    public PixKeyAlreadyExistsException() {
        super("Chave PIX já cadastrada");
    }
}
