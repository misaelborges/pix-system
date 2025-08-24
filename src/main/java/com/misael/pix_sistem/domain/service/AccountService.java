package com.misael.pix_sistem.domain.service;

import com.misael.pix_sistem.domain.model.Accounts;

import java.math.BigDecimal;

public interface AccountService {

    Accounts createAccount(Accounts accounts);
    Accounts findAccountById(Long id);
    BigDecimal checkBalance(Long id);
    Accounts updateAccount(Accounts accounts);
}
