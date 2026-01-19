package com.misael.pix_sistem.api.docs.account;

import com.misael.pix_sistem.api.dto.request.TransactionRequestDTO;
import com.misael.pix_sistem.api.dto.response.TransactionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Transaction", description = "Gerencia transferências via PIX")
public interface TransactionControllerOpenApi {

    @Operation(
            summary = "Realiza uma transferência PIX",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Transferência realizada com sucesso",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos para realizar a transferência"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta de origem ou destino não encontrada"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflito na transferência (ex: saldo insuficiente)"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor"
                    )
            }
    )
    ResponseEntity<TransactionResponseDTO> transaction(
            @Parameter(description = "Dados necessários para realizar a transferência PIX")
            TransactionRequestDTO transactionRequestDTO
    );

    @Operation(
            summary = "Consulta uma transferência pelo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Transferência encontrada",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Transferência não encontrada"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor"
                    )
            }
    )
    ResponseEntity<TransactionResponseDTO> consultTransfers(
            @Parameter(description = "Id da transferência", example = "1")
            Long id
    );

    @Operation(
            summary = "Consulta o histórico de transferências de uma conta",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Histórico de transferências retornado com sucesso",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrada"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor"
                    )
            }
    )
    ResponseEntity<List<TransactionResponseDTO>> getAccountTransactionHistory(
            @Parameter(description = "Id da conta", example = "1")
            Long accountId
    );
}
