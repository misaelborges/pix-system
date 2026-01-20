package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.api.dto.request.PaymentRequestDTO;
import com.misael.pix_sistem.api.dto.response.PaymentResponseDTO;
import com.misael.pix_sistem.core.config.mapper.TransactionMapper;
import com.misael.pix_sistem.domain.exceptions.InsufficientBalanceException;
import com.misael.pix_sistem.domain.exceptions.TransactionNotFoundException;
import com.misael.pix_sistem.domain.model.Account;
import com.misael.pix_sistem.domain.model.Transaction;
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
class PaymentServiceImplTest {

    @Mock
    private TransactionsRepository transactionsRepository;

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private PaymentServiceImpl transactionService;

    private Account sender;
    private Account receiver;
    private PaymentRequestDTO paymentRequestDTO;
    private PaymentResponseDTO paymentResponseDTO;
    private Transaction transaction;

    @BeforeEach
    void setUp() {

        sender = new Account();
        sender.setId(1L);
        sender.setBalance(BigDecimal.valueOf(500));

        receiver = new Account();
        receiver.setId(2L);
        receiver.setBalance(BigDecimal.ZERO);

        paymentRequestDTO = new PaymentRequestDTO(
                1L, 2L, BigDecimal.valueOf(100), "Pagamento"
        );

        transaction = new Transaction();
        transaction.setId(1L);

        paymentResponseDTO = new PaymentResponseDTO("Misael", "João", BigDecimal.valueOf(100),
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
                .thenReturn(paymentResponseDTO);

        PaymentResponseDTO result =
                transactionService.transaction(paymentRequestDTO);

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
                transactionService.transaction(paymentRequestDTO)
        );
    }

    @Test
    @DisplayName("Deve buscar uma transferência pelo ID com sucesso")
    void shouldConsultTransferSuccessfully() {

        when(transactionsRepository.findById(1L))
                .thenReturn(Optional.of(transaction));

        when(transactionMapper.toDTO(transaction))
                .thenReturn(paymentResponseDTO);

        PaymentResponseDTO result =
                transactionService.consultTransfers(1L);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a transferência não for encontrada")
    void shouldThrowExceptionWhenTransferNotFound() {

        when(transactionsRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () ->
                transactionService.consultTransfers(1L)
        );
    }

    @Test
    @DisplayName("Deve retornar o histórico de transações da conta")
    void shouldReturnAccountTransactionHistory() {

        when(accountsRepository.findById(1L))
                .thenReturn(Optional.of(sender));

        when(transactionsRepository.findAllByAccountId(1L))
                .thenReturn(List.of(transaction));

        when(transactionMapper.toListDTO(any()))
                .thenReturn(List.of(paymentResponseDTO));

        List<PaymentResponseDTO> result =
                transactionService.getAccountTransactionHistory(1L);

        assertFalse(result.isEmpty());
    }
}