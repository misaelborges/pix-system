package com.misael.pix_sistem.api.controller;

import com.misael.pix_sistem.api.assemblers.AccountResponseAssembler;
import com.misael.pix_sistem.api.docs.account.AccountControllerOpenApi;
import com.misael.pix_sistem.api.dto.request.AccountsRequestDTO;
import com.misael.pix_sistem.api.dto.request.AccountUpdateRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountBalanceResponseDTO;
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
public class AccountController implements AccountControllerOpenApi {

    private final AccountServiceImpl accountService;
    private final AccountResponseAssembler responseAssembler;

    public AccountController(AccountServiceImpl accountService, AccountResponseAssembler responseAssembler) {
        this.accountService = accountService;
        this.responseAssembler = responseAssembler;
    }

    @Override
    @PostMapping
    public ResponseEntity<EntityModel<AccountsResponseDTO>> createAccount(@RequestBody @Valid AccountsRequestDTO accountsRequestDTO) {
        AccountsResponseDTO accountsResponseDTO  = accountService.createAccount(accountsRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseAssembler.toModel(accountsResponseDTO));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AccountsResponseDTO>> findAccountById(@PathVariable Long id) {
        AccountsResponseDTO accountsResponseDTO = accountService.findAccountById(id);
        return ResponseEntity.ok(responseAssembler.toModel(accountsResponseDTO));
    }

    @Override
    @GetMapping("/{id}/balance")
    public ResponseEntity<EntityModel<AccountBalanceResponseDTO>> consultBalance(@PathVariable Long id) {
        AccountBalanceResponseDTO accountBalanceResponseDTO = accountService.checkBalance(id);
        return ResponseEntity.ok(responseAssembler.balanceToDto(accountBalanceResponseDTO));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AccountsResponseDTO>> updateAccount(@PathVariable Long id,
                                                                          @RequestBody @Valid AccountUpdateRequestDTO dto) {


        AccountsResponseDTO accountsResponseDTO = accountService.updateAccount(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseAssembler.toModel(accountsResponseDTO));
    }

}
