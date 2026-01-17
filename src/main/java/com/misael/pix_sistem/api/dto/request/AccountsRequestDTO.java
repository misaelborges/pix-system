package com.misael.pix_sistem.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Schema(
        name = "AccountRequestDTO",
        description = "Requisição para salvar uma account"
)
public record AccountsRequestDTO(

        @Schema(description = "Nome do dono da account", example = "Misael Borges Cancelier")
        @NotBlank(message = "O nome não pode ser em branco")
        String name,

        @Schema(description = "CPF do dono da account", example = "95582353003")
        @CPF(message = "O CPF não pode ser em branco")
        String cpf,

        @Schema(description = "Email do dono da account", example = "misael.email@gmail.com")
        @Email(message = "O Email não pode ser em branco")
        @Column(unique = true)
        String email,

        @Schema(description = "Número de contato do dono da account", example = "11999998888")
        @Column(unique = true)
        String phone) {
}
