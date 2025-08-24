package com.misael.pix_sistem.api.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record AccountRequestDTO(
        @NotBlank(message = "O nome não pode ser em branco")
        String name,

        @CPF(message = "O CPF não pode ser em branco")
        String cpf,

        @Email(message = "O Email não pode ser em branco")
        @Column(unique = true)
        String email,

        @Column(unique = true)
        String phone) {
}
