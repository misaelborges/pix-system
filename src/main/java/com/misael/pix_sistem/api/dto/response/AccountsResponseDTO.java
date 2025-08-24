package com.misael.pix_sistem.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(
        name = "AccountsResponseDTO",
        description = "Resposta contendo todas as informações da account"
)
public record AccountsResponseDTO(

        @Schema(description = "Id da account", example = "1")
        Long id,

        @Schema(description = "Nome do dono da account", example = "Misael Borges Cancelier")
        String name,

        @Schema(description = "CPF do dono da account", example = "95582353003")
        String cpf,

        @Schema(description = "Email do dono da account", example = "joao.silva@email.com")
        String email,

        @Schema(description = "Número de contato do dono da account", example = "11999998888")
        String phone,

        @Schema(description = "Valor da account", example = "1.99")
        BigDecimal balance,

        @Schema(description = "Data de criação da account", example = "2025-08-22T20:37:07.035809Z")
        OffsetDateTime created_at,

        @Schema(description = "Data da ultima atualização da account", example = "2025-08-22T20:37:07.035809Z")
        OffsetDateTime updated_at){
}
