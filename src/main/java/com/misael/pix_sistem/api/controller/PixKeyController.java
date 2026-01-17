package com.misael.pix_sistem.api.controller;

import com.misael.pix_sistem.api.dto.request.PixKeyRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountPixKeyResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixKeyResponseDTO;
import com.misael.pix_sistem.core.config.mapper.PixKeyMapper;
import com.misael.pix_sistem.domain.model.PixKeys;
import com.misael.pix_sistem.domain.service.impl.PixKeyServiceImpl;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PixKeyController {

    private final PixKeyServiceImpl pixKeyService;
    private final PixKeyMapper pixKeyMapper;

    public PixKeyController(PixKeyServiceImpl pixKeyService, PixKeyMapper pixKeyMapper) {
        this.pixKeyService = pixKeyService;
        this.pixKeyMapper = pixKeyMapper;
    }

    @GetMapping("/accounts/{id}/pix-keys")
    public AccountPixKeyResponseDTO listPixKey(@PathVariable Long id) {
        List<PixKeys> pixKeys = pixKeyService.listPixKey(id);
        return pixKeyMapper.toAccountPixKeyResponseDTO(id, pixKeys);
    }

    @PostMapping("/accounts/{id}/pix-keys")
    public PixKeyResponseDTO createPixKey(@PathVariable Long id , @RequestBody @Valid PixKeyRequestDTO pixKeyRequestDTO) {
        PixKeys pixKeys = pixKeyMapper.toEntity(pixKeyRequestDTO);
        pixKeys = pixKeyService.createPixKey(pixKeys);
        return pixKeyMapper.toResponseDTO(pixKeys);
    }
}
