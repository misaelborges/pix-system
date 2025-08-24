package com.misael.pix_sistem.api.controller;

import com.misael.pix_sistem.api.assemblers.AccountResponseAssembler;
import com.misael.pix_sistem.api.dto.request.AccountRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountsResponseDTO;
import com.misael.pix_sistem.core.config.mapper.AccountMapper;
import com.misael.pix_sistem.domain.model.Accounts;
import com.misael.pix_sistem.domain.service.impl.AccountServiceImpl;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;
    private final AccountMapper accountMapper;
    private final AccountResponseAssembler responseAssembler;

    public AccountController(AccountServiceImpl accountService, AccountMapper mapStructConfig, AccountResponseAssembler responseAssembler) {
        this.accountService = accountService;
        this.accountMapper = mapStructConfig;
        this.responseAssembler = responseAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<AccountsResponseDTO>> createAccount(@RequestBody @Valid AccountRequestDTO accountRequestDTO) {
        Accounts accounts = accountMapper.toEntity(accountRequestDTO);
        accounts = accountService.createAccount(accounts);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseAssembler.toModel(accounts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AccountsResponseDTO>> findAccountById(@PathVariable Long id) {
        Accounts accounts = accountService.findAccountById(id);
        return ResponseEntity.ok(responseAssembler.toModel(accounts));
    }
}
