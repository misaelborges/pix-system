package com.misael.pix_sistem.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        @NotNull(message = "Remetente é obrigatório")
        Long senderId,

        @NotNull(message = "Destinatário é obrigatório")
        Long receiverId,

        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser maior que R$0.00")
        BigDecimal amount,
        String description) {
}
