package com.misael.pix_sistem.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "AccountUpdateRequestDTO",
        description = "Requisição pra atualizar uma account"
)
public record AccountUpdateRequestDTO(

        @Schema(description = "Email do dono da account", example = "misael.email@gmail.com")
        @Email(message = "O Email não pode ser em branco")
        @Column(unique = true)
        String email,

        @Schema(description = "Número de contato do dono da account", example = "11999998888")
        @Column(unique = true)
        String phone) {
}
