package com.misael.pix_sistem.domain.service;

import com.misael.pix_sistem.domain.model.Accounts;

public interface AccountService {

    Accounts createAccount(Accounts accounts);
    Accounts findAccountById(Long id);
    Accounts checkBalance(Long id);
    Accounts updateAccount(Long id, Accounts accounts);
}
