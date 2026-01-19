package com.misael.pix_sistem.api.controller;

import com.misael.pix_sistem.api.docs.account.PixKeyControllerOpenApi;
import com.misael.pix_sistem.api.dto.request.PixKeysRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountPixKeyResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixKeysResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixResponseResumoDTO;
import com.misael.pix_sistem.core.config.mapper.PixKeyMapper;
import com.misael.pix_sistem.domain.service.impl.PixKeyServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PixKeyController implements PixKeyControllerOpenApi {

    private final PixKeyServiceImpl pixKeyService;
    private final PixKeyMapper pixKeyMapper;

    public PixKeyController(PixKeyServiceImpl pixKeyService, PixKeyMapper pixKeyMapper) {
        this.pixKeyService = pixKeyService;
        this.pixKeyMapper = pixKeyMapper;
    }

    @GetMapping("/accounts/{id}/pix-keys")
    public ResponseEntity<AccountPixKeyResponseDTO> listPixKey(@PathVariable Long id) {
        AccountPixKeyResponseDTO accountPixKeyResponseDTO = pixKeyService.listPixKey(id);
        return ResponseEntity.status(HttpStatus.OK).body(accountPixKeyResponseDTO);
    }

    @PostMapping("/accounts/{id}/pix-keys")
    public ResponseEntity<PixKeysResponseDTO> createPixKey(@PathVariable Long id , @RequestBody @Valid PixKeysRequestDTO pixKeysRequestDTO) {
        PixKeysResponseDTO pixKeysResponseDTO = pixKeyService.createPixKey(pixKeysRequestDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(pixKeysResponseDTO);
    }

    @GetMapping("/pix-keys/validate/{pixKey}")
    public ResponseEntity<PixResponseResumoDTO> validatePix(@PathVariable String pixKey) {
        PixResponseResumoDTO pixResponseResumoDTO = pixKeyService.validatePixKey(pixKey);
        return ResponseEntity.status(HttpStatus.OK).body(pixResponseResumoDTO);
    }

    @DeleteMapping("/pix-keys/{id}")
    public ResponseEntity<?> deletePixKey(@PathVariable Long id) {
        pixKeyService.deletePixKey(id);
        return ResponseEntity.noContent().build();
    }
}
