package com.misael.pix_sistem.domain.service;

import com.misael.pix_sistem.api.dto.request.AccountUpdateRequestDTO;
import com.misael.pix_sistem.api.dto.request.AccountsRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountBalanceResponseDTO;
import com.misael.pix_sistem.api.dto.response.AccountsResponseDTO;

public interface AccountService {

    AccountsResponseDTO createAccount(AccountsRequestDTO accountsRequestDTO);
    AccountsResponseDTO findAccountById(Long id);
    AccountBalanceResponseDTO checkBalance(Long id);
    AccountsResponseDTO updateAccount(Long id, AccountUpdateRequestDTO updateRequestDTO);
}
