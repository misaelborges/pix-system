package com.misael.pix_sistem.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "AccountPixKeyResponseDTO",
        description = "Resposta contendo as chaves PIX de uma conta"
)
public record AccountPixKeyResponseDTO(

        @Schema(description = "ID da conta", example = "1")
        Long accountId,

        @Schema(description = "Lista de chaves PIX da conta")
        List<PixKeysResponseDTO> pixKeys) {
}
