package com.misael.pix_sistem.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(
        name = "AccountBalanceResponseDTO",
        description = "Resposta contendo saldo e informações da account"
)
public record AccountBalanceResponseDTO(

        @Schema(description = "Id da account", example = "1")
        Long id,

        @Schema(description = "Valor da account", example = "1.99")
        BigDecimal balance,

        @Schema(description = "Data da ultima atualização da account", example = "2025-08-22T20:37:07.035809Z")
        OffsetDateTime updated_at) {
}
