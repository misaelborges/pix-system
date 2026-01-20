package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.api.dto.request.PaymentRequestDTO;
import com.misael.pix_sistem.api.dto.response.PaymentResponseDTO;
import com.misael.pix_sistem.core.config.mapper.TransactionMapper;
import com.misael.pix_sistem.domain.exceptions.AccountNotFoundException;
import com.misael.pix_sistem.domain.exceptions.InsufficientBalanceException;
import com.misael.pix_sistem.domain.exceptions.TransactionNotFoundException;
import com.misael.pix_sistem.domain.model.Account;
import com.misael.pix_sistem.domain.model.Transaction;
import com.misael.pix_sistem.domain.repository.AccountsRepository;
import com.misael.pix_sistem.domain.repository.TransactionsRepository;
import com.misael.pix_sistem.domain.service.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final TransactionsRepository transactionsRepository;
    private final AccountsRepository accountsRepository;
    private final TransactionMapper transactionMapper;

    public PaymentServiceImpl(TransactionsRepository transactionsRepository,
                              AccountsRepository accountsRepository,
                              TransactionMapper transactionMapper) {
        this.accountsRepository = accountsRepository;
        this.transactionMapper = transactionMapper;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    @Transactional
    public PaymentResponseDTO transaction(PaymentRequestDTO paymentRequestDTO) {
        Account sender = searchAccounts(paymentRequestDTO.senderId());
        Account receiver = searchAccounts(paymentRequestDTO.receiverId());

        if (sender.getBalance().compareTo(paymentRequestDTO.amount()) < 0) {
            throw new InsufficientBalanceException();
        }

        sender.debit(paymentRequestDTO.amount());
        receiver.credit(paymentRequestDTO.amount());

        Transaction transaction = new Transaction();
        transaction.setSenderId(sender);
        transaction.setReceiverId(receiver);
        transaction.setAmount(paymentRequestDTO.amount());
        transaction.setDescription(paymentRequestDTO.description());
        transaction.setPixKeyReceiver("");

        accountsRepository.save(sender);
        accountsRepository.save(receiver);
        transactionsRepository.save(transaction);

        return transactionMapper.toDTO(transaction);

    }

    @Override
    public PaymentResponseDTO consultTransfers(Long id) {
        Transaction transaction = transactionsRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
        return transactionMapper.toDTO(transaction);
    }

    @Override
    public List<PaymentResponseDTO> getAccountTransactionHistory(Long accountId) {
        searchAccounts(accountId);
        List<Transaction> transactions = transactionsRepository.findAllByAccountId(accountId);
        return transactionMapper.toListDTO(transactions);
    }

    private Account searchAccounts(Long id) {
        return accountsRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
}
