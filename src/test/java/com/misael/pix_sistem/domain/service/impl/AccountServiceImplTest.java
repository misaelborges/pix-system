package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.api.dto.request.AccountUpdateRequestDTO;
import com.misael.pix_sistem.api.dto.request.AccountsRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountBalanceResponseDTO;
import com.misael.pix_sistem.api.dto.response.AccountsResponseDTO;
import com.misael.pix_sistem.core.config.mapper.AccountMapper;
import com.misael.pix_sistem.domain.exceptions.AccountNotFoundException;
import com.misael.pix_sistem.domain.exceptions.EmailAlreadyExistsException;
import com.misael.pix_sistem.domain.model.Accounts;
import com.misael.pix_sistem.domain.repository.AccountsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Classe responsavel por fazer os testes de AccountServiceImpl")
class AccountServiceImplTest {

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Accounts accounts;
    private AccountsRequestDTO accountsRequestDTO;
    private AccountsResponseDTO accountsResponseDTO;
    private AccountBalanceResponseDTO accountBalanceResponseDTO;
    private AccountUpdateRequestDTO updateRequestDTO;

    @BeforeEach
    void setUp() {

        accounts = new Accounts(
                1L, "Misael Borges Cancelier", "84082417028", "mizael.email@test.com",
                "48996814955", BigDecimal.ZERO, OffsetDateTime.now(), OffsetDateTime.now());

        accountsRequestDTO = new AccountsRequestDTO(
                "Misael Borges Cancelier", "84082417028", "mizael.email@test.com", "48996814955");

        accountsResponseDTO = new AccountsResponseDTO(
                1L, "Misael Borges Cancelier", "84082417028", "mizael.email@test.com",
                "48996814955", BigDecimal.ZERO, OffsetDateTime.now(), OffsetDateTime.now());

        accountBalanceResponseDTO = new AccountBalanceResponseDTO(1L, BigDecimal.valueOf(100L), OffsetDateTime.now());

        updateRequestDTO = new AccountUpdateRequestDTO("email@novo.com", "48996814933");
    }

    @Test
    @DisplayName("Deve criar uma nova conta com sucesso")
    void shouldCreateAccountSuccessfully() {
        when(accountMapper.toEntity(accountsRequestDTO)).thenReturn(accounts);
        when(accountsRepository.save(accounts)).thenReturn(accounts);
        when(accountMapper.toDto(accounts)).thenReturn(accountsResponseDTO);

        AccountsResponseDTO result = accountService.createAccount(accountsRequestDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Misael Borges Cancelier", result.name());

        verify(accountMapper, times(1)).toEntity(accountsRequestDTO);
        verify(accountsRepository, times(1)).save(accounts);
        verify(accountMapper, times(1)).toDto(accounts);

    }

    @Test
    @DisplayName("Deve buscar uma conta pelo ID com sucesso")
    void shouldFindAccountByIdSuccessfully() {

        when(accountsRepository.findById(1L)).thenReturn(Optional.of(accounts));
        when(accountMapper.toDto(accounts)).thenReturn(accountsResponseDTO);

        AccountsResponseDTO result = accountService.findAccountById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Misael Borges Cancelier", result.name());

        verify(accountsRepository, times(1)).findById(1L);
        verify(accountMapper, times(1)).toDto(accounts);

    }

    @Test
    @DisplayName("Deve lançar exceção quando a conta não for encontrada ao buscar por ID")
    void shouldThrowExceptionWhenAccountNotFoundOnFindById() {

        when(accountsRepository.findById(10L)).thenReturn(Optional.empty());


        assertThrows(AccountNotFoundException.class, () -> {
            accountService.findAccountById(10L);
        });

    }

    @Test
    @DisplayName("Deve retornar o saldo atual da conta")
    void shouldReturnAccountBalance() {
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(accounts));
        when(accountMapper.balanceToDto(accounts)).thenReturn(accountBalanceResponseDTO);

        AccountBalanceResponseDTO result = accountService.checkBalance(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(BigDecimal.valueOf(100L), result.balance());

        verify(accountsRepository, times(1)).findById(1L);
        verify(accountMapper, times(1)).balanceToDto(accounts);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a conta não for encontrada ao consultar saldo")
    void shouldThrowExceptionWhenAccountNotFoundOnCheckBalance() {

        when(accountsRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () ->
                accountService.checkBalance(1L)
        );
    }

    @Test
    @DisplayName("Deve atualizar os dados da conta com sucesso")
    void shouldUpdateAccountSuccessfully() {
        when(accountsRepository.findById(1L)).thenReturn(Optional.of(accounts));

        when(accountsRepository.existsByEmail(updateRequestDTO.email())).thenReturn(false);

        when(accountMapper.toDto(accounts)).thenReturn(accountsResponseDTO);

        AccountsResponseDTO result = accountService.updateAccount(1L, updateRequestDTO);

        assertNotNull(result);
        assertEquals(accountsResponseDTO, result);

        verify(accountMapper).updateEntityFromDto(updateRequestDTO, accounts);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar atualizar a conta com um email já existente")
    void shouldThrowExceptionWhenUpdatingAccountWithExistingEmail() {
        when(accountsRepository.findById(1L))
                .thenReturn(Optional.of(accounts));

        when(accountsRepository.existsByEmail(updateRequestDTO.email()))
                .thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () ->
                accountService.updateAccount(1L, updateRequestDTO));
    }
}