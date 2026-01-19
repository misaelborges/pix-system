package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.api.dto.request.PaymentRequestDTO;
import com.misael.pix_sistem.api.dto.response.PaymentResponseDTO;
import com.misael.pix_sistem.core.config.mapper.TransactionMapper;
import com.misael.pix_sistem.domain.exceptions.AccountNotFoundException;
import com.misael.pix_sistem.domain.exceptions.InsufficientBalanceException;
import com.misael.pix_sistem.domain.exceptions.TransactionNotFoundException;
import com.misael.pix_sistem.domain.model.Accounts;
import com.misael.pix_sistem.domain.model.Transactions;
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
        Accounts sender = searchAccounts(paymentRequestDTO.senderId());
        Accounts receiver = searchAccounts(paymentRequestDTO.receiverId());

        if (sender.getBalance().compareTo(paymentRequestDTO.amount()) < 0) {
            throw new InsufficientBalanceException("Valor insuficiente para transação");
        }

        sender.debit(paymentRequestDTO.amount());
        receiver.credit(paymentRequestDTO.amount());

        Transactions transactions = new Transactions();
        transactions.setSenderId(sender);
        transactions.setReceiverId(receiver);
        transactions.setAmount(paymentRequestDTO.amount());
        transactions.setDescription(paymentRequestDTO.description());
        transactions.setPixKeyReceiver("");

        accountsRepository.save(sender);
        accountsRepository.save(receiver);
        transactionsRepository.save(transactions);

        return transactionMapper.toDTO(transactions);

    }

    @Override
    public PaymentResponseDTO consultTransfers(Long id) {
        Transactions transactions = transactionsRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
        return transactionMapper.toDTO(transactions);
    }

    @Override
    public List<PaymentResponseDTO> getAccountTransactionHistory(Long accountId) {
        searchAccounts(accountId);
        List<Transactions> transactions = transactionsRepository.findAllByAccountId(accountId);
        return transactionMapper.toListDTO(transactions);
    }

    private Accounts searchAccounts(Long id) {
        return accountsRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
}
