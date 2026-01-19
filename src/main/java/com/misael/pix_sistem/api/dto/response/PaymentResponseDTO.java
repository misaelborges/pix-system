package com.misael.pix_sistem.api.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PaymentResponseDTO(String sender,
                                 String receiver,
                                 BigDecimal amount,
                                 OffsetDateTime transferDate) {
}
