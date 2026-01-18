package com.misael.pix_sistem.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransferNotFoundException extends RuntimeException{

    public TransferNotFoundException(String message) {
        super(message);
    }

    public TransferNotFoundException(Long id) {
        super(String.format("Não foi encontrado uma transferencia com o id: %s", id));
    }
}
