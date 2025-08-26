package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.domain.model.Accounts;
import com.misael.pix_sistem.domain.repository.AccountsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Classe responsavel por fazer os testes de AccountServiceImpl")
class AccountServiceImplTest {

    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("Metodo testa a criação de um novo Account")
    void createAccount() {
        Accounts accounts = new Accounts(
                1L, "Misael Borges Cancelier", "84082417028", "mizael.email@test.com",
                "48996814955", BigDecimal.ZERO, OffsetDateTime.now(), OffsetDateTime.now());

        when(accountsRepository.save(accounts)).thenReturn(accounts);

        Accounts resultado = accountService.createAccount(accounts);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getName()).isEqualTo(accounts.getName());
        verify(accountsRepository).save(accounts);


    }

    @Test
    void findAccountById() {
    }

    @Test
    void checkBalance() {
    }

    @Test
    void updateAccount() {
    }
}