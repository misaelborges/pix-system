package com.misael.pix_sistem.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "PixKeysRequestDTO",
        description = "Requisição para cadastrar uma chave PIX"
)
public record PixKeysRequestDTO(

        @Schema(description = "ID da conta vinculada à chave PIX", example = "1")
        @NotNull(message = "Valor inválido")
        Long accountsId,

        @Schema(description = "Valor da chave PIX", example = "11999998888")
        @NotBlank(message = "Valor inválido")
        String keyValue,

        @Schema(description = "Tipo da chave PIX", example = "PHONE")
        @NotBlank(message = "Valor inválido")
        String keyType
) {
}
