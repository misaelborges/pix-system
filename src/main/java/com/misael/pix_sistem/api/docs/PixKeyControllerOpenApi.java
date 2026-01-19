package com.misael.pix_sistem.api.docs;

import com.misael.pix_sistem.api.dto.request.PixKeysRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountPixKeyResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixKeysResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixResponseResumoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pix Keys", description = "Operações relacionadas às chaves Pix")
public interface PixKeyControllerOpenApi {

    @Operation(
            summary = "Listar chaves Pix da conta",
            description = "Retorna todas as chaves Pix ativas vinculadas a uma conta"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chaves Pix listadas com sucesso",
                    content = @Content(schema = @Schema(implementation = AccountPixKeyResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada",
                    content = @Content)
    })
    ResponseEntity<AccountPixKeyResponseDTO> listPixKey(Long id);

    @Operation(
            summary = "Criar uma nova chave Pix",
            description = "Cria uma nova chave Pix para uma conta, respeitando as regras de negócio"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chave Pix criada com sucesso",
                    content = @Content(schema = @Schema(implementation = PixKeysResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada",
                    content = @Content)
    })
    ResponseEntity<PixKeysResponseDTO> createPixKey(Long id, PixKeysRequestDTO pixKeysRequestDTO);

    @Operation(
            summary = "Validar uma chave Pix",
            description = "Valida se uma chave Pix existe e está ativa"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chave Pix válida",
                    content = @Content(schema = @Schema(implementation = PixResponseResumoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Chave Pix não encontrada",
                    content = @Content)
    })
    ResponseEntity<PixResponseResumoDTO> validatePix(String pixKey);

    @Operation(
            summary = "Excluir uma chave Pix",
            description = "Remove logicamente uma chave Pix (soft delete)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Chave Pix removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Chave Pix não encontrada",
                    content = @Content)
    })
    ResponseEntity<?> deletePixKey(Long id);
}
