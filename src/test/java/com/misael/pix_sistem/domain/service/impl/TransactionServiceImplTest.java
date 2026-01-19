package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.api.dto.request.TransactionRequestDTO;
import com.misael.pix_sistem.api.dto.response.TransactionResponseDTO;
import com.misael.pix_sistem.core.config.mapper.TransactionMapper;
import com.misael.pix_sistem.domain.exceptions.InsufficientBalanceException;
import com.misael.pix_sistem.domain.exceptions.TransferNotFoundException;
import com.misael.pix_sistem.domain.model.Accounts;
import com.misael.pix_sistem.domain.model.Transactions;
import com.misael.pix_sistem.domain.repository.AccountsRepository;
import com.misael.pix_sistem.domain.repository.TransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Classe responsavel por fazer os testes de TransactionServiceImpl")
class TransactionServiceImplTest {

    @Mock
    private TransactionsRepository transactionsRepository;

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Accounts sender;
    private Accounts receiver;
    private TransactionRequestDTO transactionRequestDTO;
    private TransactionResponseDTO transactionResponseDTO;
    private Transactions transactions;

    @BeforeEach
    void setUp() {

        sender = new Accounts();
        sender.setId(1L);
        sender.setBalance(BigDecimal.valueOf(500));

        receiver = new Accounts();
        receiver.setId(2L);
        receiver.setBalance(BigDecimal.ZERO);

        transactionRequestDTO = new TransactionRequestDTO(
                1L, 2L, BigDecimal.valueOf(100), "Pagamento"
        );

        transactions = new Transactions();
        transactions.setId(1L);

        transactionResponseDTO = new TransactionResponseDTO("Misael", "João", BigDecimal.valueOf(100),
                OffsetDateTime.now());
    }

    @Test
    @DisplayName("Deve realizar uma transação com sucesso")
    void shouldPerformTransactionSuccessfully() {

        when(accountsRepository.findById(1L))
                .thenReturn(Optional.of(sender));

        when(accountsRepository.findById(2L))
                .thenReturn(Optional.of(receiver));

        when(transactionMapper.toDTO(any()))
                .thenReturn(transactionResponseDTO);

        TransactionResponseDTO result =
                transactionService.transaction(transactionRequestDTO);

        assertNotNull(result);

        verify(accountsRepository, times(1)).save(sender);
        verify(accountsRepository, times(1)).save(receiver);
        verify(transactionsRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o saldo for insuficiente")
    void shouldThrowExceptionWhenInsufficientBalance() {

        sender.setBalance(BigDecimal.valueOf(50));

        when(accountsRepository.findById(1L))
                .thenReturn(Optional.of(sender));

        when(accountsRepository.findById(2L))
                .thenReturn(Optional.of(receiver));

        assertThrows(InsufficientBalanceException.class, () ->
                transactionService.transaction(transactionRequestDTO)
        );
    }

    @Test
    @DisplayName("Deve buscar uma transferência pelo ID com sucesso")
    void shouldConsultTransferSuccessfully() {

        when(transactionsRepository.findById(1L))
                .thenReturn(Optional.of(transactions));

        when(transactionMapper.toDTO(transactions))
                .thenReturn(transactionResponseDTO);

        TransactionResponseDTO result =
                transactionService.consultTransfers(1L);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a transferência não for encontrada")
    void shouldThrowExceptionWhenTransferNotFound() {

        when(transactionsRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(TransferNotFoundException.class, () ->
                transactionService.consultTransfers(1L)
        );
    }

    @Test
    @DisplayName("Deve retornar o histórico de transações da conta")
    void shouldReturnAccountTransactionHistory() {

        when(accountsRepository.findById(1L))
                .thenReturn(Optional.of(sender));

        when(transactionsRepository.findAllByAccountId(1L))
                .thenReturn(List.of(transactions));

        when(transactionMapper.toListDTO(any()))
                .thenReturn(List.of(transactionResponseDTO));

        List<TransactionResponseDTO> result =
                transactionService.getAccountTransactionHistory(1L);

        assertFalse(result.isEmpty());
    }
}