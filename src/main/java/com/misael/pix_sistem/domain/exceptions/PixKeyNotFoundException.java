package com.misael.pix_sistem.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PixKeyNotFoundException extends EntityNotFoundException {

    public PixKeyNotFoundException(String key) {
        super("Chave PIX '" + key + "' não encontrada");
    }

    public PixKeyNotFoundException() {
        super("Nenhuma Chave PIX encontrada");
    }
}
