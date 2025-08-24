package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.domain.model.Accounts;
import com.misael.pix_sistem.domain.repository.AccountsRepository;
import com.misael.pix_sistem.domain.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Accounts updateAccount(Long id, Accounts accounts) {
        Accounts account = findAccountById(id);
        if (!accounts.getEmail().equals(account.getEmail())) {
            if (accountsRepository.existsByEmail(accounts.getEmail())) {
                throw new RuntimeException("Email já esta em uso");
            }
            BeanUtils.copyProperties(accounts, account, "id", "cpf", "created_at", "balance");
        } else {
            BeanUtils.copyProperties(accounts, account, "id", "cpf", "created_at", "email", "balance");
        }

        return accountsRepository.save(account);
    }
}
