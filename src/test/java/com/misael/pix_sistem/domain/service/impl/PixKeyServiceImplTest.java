package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.api.dto.request.PixKeysRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountPixKeyResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixKeysResponseDTO;
import com.misael.pix_sistem.core.config.mapper.PixKeyMapper;
import com.misael.pix_sistem.domain.exceptions.AccountNotFoundException;
import com.misael.pix_sistem.domain.exceptions.MaxPixKeysLimitException;
import com.misael.pix_sistem.domain.exceptions.PixKeyAlreadyExistsException;
import com.misael.pix_sistem.domain.model.Account;
import com.misael.pix_sistem.domain.model.PixKey;
import com.misael.pix_sistem.domain.repository.AccountsRepository;
import com.misael.pix_sistem.domain.repository.PixKeysRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Classe responsavel por fazer os testes de PixKeyServiceImpl")
class PixKeyServiceImplTest {

    @Mock
    private PixKeysRepository pixKeysRepository;

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private PixKeyMapper pixKeyMapper;

    @InjectMocks
    private PixKeyServiceImpl pixKeyService;

    private Account account;
    private PixKey pixKey;
    private PixKeysRequestDTO pixKeysRequestDTO;
    private PixKeysResponseDTO pixKeysResponseDTO;
    private AccountPixKeyResponseDTO accountPixKeyResponseDTO;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setId(1L);

        pixKey = new PixKey();
        pixKey.setId(1L);
        pixKey.setActive(true);

        pixKeysRequestDTO = new PixKeysRequestDTO(1L, "Email", "teste@email.com"
        );

        pixKeysResponseDTO = new PixKeysResponseDTO(1L, "teste@email.com", "Email", true, OffsetDateTime.now());

        accountPixKeyResponseDTO = new AccountPixKeyResponseDTO(1L, List.of(pixKeysResponseDTO));
    }

    @Test
    @DisplayName("Deve listar as chaves Pix ativas da conta")
    void shouldListPixKeysSuccessfully() {

        when(pixKeysRepository.findByAccountIdIdAndActiveTrue(1L)).thenReturn(List.of(pixKey));

        when(pixKeyMapper.toAccountPixKeyResponseDTO(1L, List.of(pixKey)))
                .thenReturn(accountPixKeyResponseDTO);

        AccountPixKeyResponseDTO result = pixKeyService.listPixKey(1L);

        assertNotNull(result);
        assertEquals(1L, result.accountId());

        verify(pixKeysRepository, times(1)).findByAccountIdIdAndActiveTrue(1L);
    }

    @Test
    @DisplayName("Deve criar uma chave Pix com sucesso")
    void shouldCreatePixKeySuccessfully() {

        when(accountsRepository.findById(1L)).thenReturn(Optional.of(account));

        when(pixKeysRepository.countByAccountIdIdAndActiveTrue(1L)).thenReturn(0);

        when(pixKeysRepository.existsByAccountIdIdAndKeyTypeAndActiveTrue(1L, pixKeysRequestDTO.keyType()))
                .thenReturn(false);

        when(pixKeyMapper.toEntity(pixKeysRequestDTO)).thenReturn(pixKey);

        when(pixKeysRepository.save(pixKey)).thenReturn(pixKey);

        when(pixKeyMapper.toResponseDTO(pixKey)).thenReturn(pixKeysResponseDTO);

        PixKeysResponseDTO result = pixKeyService.createPixKey(pixKeysRequestDTO);

        assertNotNull(result);
        verify(pixKeysRepository, times(1)).save(pixKey);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a conta não existir ao criar chave Pix")
    void shouldThrowExceptionWhenAccountNotFoundOnCreatePixKey() {

        when(accountsRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> pixKeyService.createPixKey(pixKeysRequestDTO));
    }

    @Test
    @DisplayName("Deve lançar exceção quando exceder o limite de chaves Pix")
    void shouldThrowExceptionWhenExceedingPixKeyLimit() {

        when(accountsRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(pixKeysRepository.countByAccountIdIdAndActiveTrue(1L))
                .thenReturn(5);

        assertThrows(MaxPixKeysLimitException.class, () -> pixKeyService.createPixKey(pixKeysRequestDTO));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a chave Pix já existir")
    void shouldThrowExceptionWhenPixKeyAlreadyExists() {

        when(accountsRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(pixKeysRepository.countByAccountIdIdAndActiveTrue(1L))
                .thenReturn(1);

        when(pixKeysRepository.existsByAccountIdIdAndKeyTypeAndActiveTrue(1L, pixKeysRequestDTO.keyType()))
                .thenReturn(true);

        assertThrows(PixKeyAlreadyExistsException.class, () -> pixKeyService.createPixKey(pixKeysRequestDTO));
    }

    @Test
    @DisplayName("Deve deletar uma chave Pix com sucesso")
    void shouldDeletePixKeySuccessfully() {

        when(pixKeysRepository.findById(1L)).thenReturn(Optional.of(pixKey));

        pixKeyService.deletePixKey(1L);

        assertFalse(pixKey.isActive());
    }
}
