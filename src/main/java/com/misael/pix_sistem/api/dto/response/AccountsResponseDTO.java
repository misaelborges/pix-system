package com.misael.pix_sistem.api.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AccountsResponseDTO(
        Long id,
        String name,
        String cpf,
        String email,
        String phone,
        BigDecimal balance,
        OffsetDateTime created_at,
        OffsetDateTime updated_at){
}
