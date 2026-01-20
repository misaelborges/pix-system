package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.api.dto.request.AccountUpdateRequestDTO;
import com.misael.pix_sistem.api.dto.request.AccountsRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountBalanceResponseDTO;
import com.misael.pix_sistem.api.dto.response.AccountsResponseDTO;
import com.misael.pix_sistem.core.config.mapper.AccountMapper;
import com.misael.pix_sistem.domain.exceptions.AccountNotFoundException;
import com.misael.pix_sistem.domain.exceptions.EmailAlreadyExistsException;
import com.misael.pix_sistem.domain.model.Accounts;
import com.misael.pix_sistem.domain.repository.AccountsRepository;
import com.misael.pix_sistem.domain.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountsRepository accountsRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountsRepository accountsRepository, AccountMapper accountMapper) {
        this.accountsRepository = accountsRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountsResponseDTO createAccount(AccountsRequestDTO accountsRequestDTO) {
        Accounts account = accountMapper.toEntity(accountsRequestDTO);
        account = accountsRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountsResponseDTO findAccountById(Long id) {
        Accounts account = accountsRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        return accountMapper.toDto(account);
    }

    @Override
    public AccountBalanceResponseDTO checkBalance(Long id) {
        Accounts account = accountsRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        return accountMapper.balanceToDto(account);
    }

    @Override
    @Transactional
    public AccountsResponseDTO updateAccount(Long id, AccountUpdateRequestDTO updateRequestDTO) {
        Accounts account = accountsRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));

        if (!updateRequestDTO.email().equals(account.getEmail())) {
            if (accountsRepository.existsByEmail(updateRequestDTO.email())) {
                throw new EmailAlreadyExistsException();
            }
        }
        accountMapper.updateEntityFromDto(updateRequestDTO, account);

        return accountMapper.toDto(account);
    }
}
