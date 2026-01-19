package com.misael.pix_sistem.api.docs;

import com.misael.pix_sistem.api.dto.request.AccountsRequestDTO;
import com.misael.pix_sistem.api.dto.request.AccountUpdateRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountBalanceResponseDTO;
import com.misael.pix_sistem.api.dto.response.AccountsResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Account", description = "Gerencia accounts")
public interface AccountControllerOpenApi {

    @Operation(
            summary = "Cadastra um novo Account",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Account cadastrada com sucesso",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos na requisição"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflito ao cadastrar Account (ex: Account já existente)"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor"
                    )
            }
    )
    ResponseEntity<EntityModel<AccountsResponseDTO>> createAccount(
            @Parameter(description = "Representação de um novo Account")
            AccountsRequestDTO accountsRequestDTO
    );

    @Operation(
            summary = "Busca um Account por id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account encontrado",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor"
                    )
            }
    )
    ResponseEntity<EntityModel<AccountsResponseDTO>> findAccountById(
            @Parameter(description = "Id de um Account", example = "1")
            Long id
    );

    @Operation(
            summary = "Consulta o balance de um Account por id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Balance retornado com sucesso",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor"
                    )
            }
    )
    ResponseEntity<EntityModel<AccountBalanceResponseDTO>> consultBalance(
            @Parameter(description = "Id de um Account", example = "1")
            Long id
    );

    @Operation(
            summary = "Atualiza um Account por id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account atualizado com sucesso",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos para atualização"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno no servidor"
                    )
            }
    )
    ResponseEntity<EntityModel<AccountsResponseDTO>> updateAccount(
            @Parameter(description = "Id de um Account", example = "1")
            Long id,

            @Parameter(description = "Representação de um Account com os novos dados")
            AccountUpdateRequestDTO dto
    );
}
