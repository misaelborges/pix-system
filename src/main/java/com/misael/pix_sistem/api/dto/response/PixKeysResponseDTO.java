package com.misael.pix_sistem.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(
        name = "PixKeysResponseDTO",
        description = "Detalhes de uma chave PIX"
)
public record PixKeysResponseDTO(

        @Schema(description = "ID da chave PIX", example = "10")
        Long id,

        @Schema(description = "Valor da chave PIX", example = "misael@email.com")
        String keyValue,

        @Schema(description = "Tipo da chave PIX", example = "EMAIL")
        String keyType,

        @Schema(description = "Indica se a chave está ativa", example = "true")
        boolean active,

        @Schema(description = "Data de criação da chave", example = "2026-01-20T09:30:00Z")
        OffsetDateTime createdAt) {
}
