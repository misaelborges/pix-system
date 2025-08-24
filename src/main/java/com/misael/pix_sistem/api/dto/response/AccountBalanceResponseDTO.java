package com.misael.pix_sistem.api.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AccountBalanceResponseDTO(Long id, BigDecimal balance, OffsetDateTime updated_at) {
}
