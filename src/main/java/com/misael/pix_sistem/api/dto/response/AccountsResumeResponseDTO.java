package com.misael.pix_sistem.api.dto.response;

public record AccountsResumeResponseDTO(
        Long id,
        String name,
        String cpf,
        String email) {
}
