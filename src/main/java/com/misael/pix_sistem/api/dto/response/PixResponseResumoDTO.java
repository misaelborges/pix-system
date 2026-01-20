package com.misael.pix_sistem.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "PixResponseResumoDTO",
        description = "Resumo de uma chave PIX vinculada a uma conta"
)
public record PixResponseResumoDTO(

        @Schema(description = "Valor da chave PIX", example = "11999998888")
        String keyValue,

        @Schema(description = "Tipo da chave PIX", example = "PHONE")
        String keyType,

        @Schema(description = "Nome do dono da conta", example = "Misael Borges")
        String nameAccount
) {
}
