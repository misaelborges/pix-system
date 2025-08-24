package com.misael.pix_sistem.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "AccountsResumeResponseDTO",
        description = "Resposta contendo apenas algumas informações da account"
)
public record AccountsResumeResponseDTO(

        @Schema(description = "Id da account", example = "1")
        Long id,

        @Schema(description = "Nome do dono da account", example = "Misael Borges Cancelier")
        String name,

        @Schema(description = "CPF do dono da account", example = "95582353003")
        String cpf,

        @Schema(description = "Email do dono da account", example = "joao.silva@email.com")
        String email) {
}
