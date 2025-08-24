package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.domain.exceptions.AccountNotFoundException;
import com.misael.pix_sistem.domain.model.Accounts;
import com.misael.pix_sistem.domain.repository.AccountsRepository;
import com.misael.pix_sistem.domain.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountsRepository accountsRepository;

    public AccountServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public Accounts createAccount(Accounts accounts) {
        return accountsRepository.save(accounts);
    }

    @Override
    public Accounts findAccountById(Long id) {
        return accountsRepository.findById(id).orElseThrow();
    }

    @Override
    public Accounts checkBalance(Long id) {
        return findAccountById(id);
    }

    @Override
    public Accounts updateAccount(Accounts accounts) {
        return null;
    }
}
