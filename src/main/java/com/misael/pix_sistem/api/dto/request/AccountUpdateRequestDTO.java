package com.misael.pix_sistem.api.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccountUpdateRequestDTO(
        @NotBlank(message = "O nome não pode ser em branco")
        String name,

        @Email(message = "O Email não pode ser em branco")
        @Column(unique = true)
        String email,

        @Column(unique = true)
        String phone) {
}
