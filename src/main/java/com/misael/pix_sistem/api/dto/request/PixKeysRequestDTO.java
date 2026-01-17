package com.misael.pix_sistem.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PixKeysRequestDTO(
        @NotNull(message = "Valor inválido")
        Long accountsId,

        @NotBlank(message = "Valor inválido")
        String keyValue,

        @NotBlank(message = "Valor inválido")
        String keyType
) {
}
