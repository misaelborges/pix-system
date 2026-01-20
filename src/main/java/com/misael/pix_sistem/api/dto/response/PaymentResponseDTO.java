package com.misael.pix_sistem.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(
        name = "PaymentResponseDTO",
        description = "Resposta de uma transferência realizada"
)
public record PaymentResponseDTO(

        @Schema(description = "Nome do remetente", example = "Misael Borges")
        String sender,

        @Schema(description = "Nome do destinatário", example = "João Silva")
        String receiver,

        @Schema(description = "Valor transferido", example = "150.75")
        BigDecimal amount,

        @Schema(description = "Data e hora da transferência", example = "2026-01-20T10:15:30Z")
        OffsetDateTime transferDate) {
}
