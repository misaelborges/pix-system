package com.misael.pix_sistem.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(
        name = "PaymentRequestDTO",
        description = "Requisição para realizar um pagamento/transferência"
)
public record PaymentRequestDTO(

        @Schema(description = "ID da conta remetente", example = "1")
        @NotNull(message = "Remetente é obrigatório")
        Long senderId,

        @Schema(description = "ID da conta destinatária", example = "2")
        @NotNull(message = "Destinatário é obrigatório")
        Long receiverId,

        @Schema(description = "Valor da transferência", example = "150.75")
        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser maior que R$0.00")
        BigDecimal amount,

        @Schema(description = "Descrição da transferência", example = "Pagamento de aluguel")
        String description) {
}
