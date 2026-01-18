package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.api.dto.request.TransactionRequestDTO;
import com.misael.pix_sistem.api.dto.response.TransactionResponseDTO;
import com.misael.pix_sistem.core.config.mapper.TransactionMapper;
import com.misael.pix_sistem.domain.exceptions.AccountNotFoundException;
import com.misael.pix_sistem.domain.exceptions.InsufficientBalanceException;
import com.misael.pix_sistem.domain.exceptions.TransferNotFoundException;
import com.misael.pix_sistem.domain.model.Accounts;
import com.misael.pix_sistem.domain.model.Transactions;
import com.misael.pix_sistem.domain.repository.AccountsRepository;
import com.misael.pix_sistem.domain.repository.TransactionsRepository;
import com.misael.pix_sistem.domain.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionsRepository transactionsRepository;
    private final AccountsRepository accountsRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionsRepository transactionsRepository,
                                  AccountsRepository accountsRepository,
                                  TransactionMapper transactionMapper) {
        this.accountsRepository = accountsRepository;
        this.transactionMapper = transactionMapper;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    @Transactional
    public TransactionResponseDTO transaction(TransactionRequestDTO transactionRequestDTO) {
        Accounts sender = searchAccounts(transactionRequestDTO.senderId());
        Accounts receiver = searchAccounts(transactionRequestDTO.receiverId());

        if (sender.getBalance().compareTo(transactionRequestDTO.amount()) < 0) {
            throw new InsufficientBalanceException("Valor insuficiente para transação");
        }

        sender.debit(transactionRequestDTO.amount());
        receiver.credit(transactionRequestDTO.amount());

        Transactions transactions = new Transactions();
        transactions.setSenderId(sender);
        transactions.setReceiverId(receiver);
        transactions.setAmount(transactionRequestDTO.amount());
        transactions.setDescription(transactionRequestDTO.description());
        transactions.setPixKeyReceiver("");

        accountsRepository.save(sender);
        accountsRepository.save(receiver);
        transactionsRepository.save(transactions);

        return transactionMapper.toDTO(transactions);

    }

    @Override
    public TransactionResponseDTO consultTransfers(Long id) {
        Transactions transactions = transactionsRepository.findById(id).orElseThrow(() -> new TransferNotFoundException(id));
        return transactionMapper.toDTO(transactions);
    }

    @Override
    public List<TransactionResponseDTO> getAccountTransactionHistory(Long accountId) {
        searchAccounts(accountId);
        List<Transactions> transactions = transactionsRepository.findAllByAccountId(accountId);
        return transactionMapper.toListDTO(transactions);
    }

    private Accounts searchAccounts(Long id) {
        return accountsRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
}
