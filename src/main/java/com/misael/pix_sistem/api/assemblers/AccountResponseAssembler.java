package com.misael.pix_sistem.api.assemblers;

import com.misael.pix_sistem.api.controller.AccountController;
import com.misael.pix_sistem.api.dto.response.AccountBalanceResponseDTO;
import com.misael.pix_sistem.api.dto.response.AccountsResponseDTO;
import com.misael.pix_sistem.core.config.mapper.AccountMapper;
import com.misael.pix_sistem.domain.model.Accounts;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountResponseAssembler {

    private final AccountMapper accountMapper;

    public AccountResponseAssembler(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public EntityModel<AccountsResponseDTO> toModel(Accounts accounts) {
        AccountsResponseDTO accountsResponseDTO = accountMapper.toDto(accounts);

        return EntityModel.of(accountsResponseDTO)
                .add(linkTo(methodOn(AccountController.class).findAccountById(accounts.getId())).withSelfRel())
                .add(linkTo(AccountController.class).withRel("accounts"));
    }

    public EntityModel<AccountBalanceResponseDTO> balanceToDto(Accounts accounts) {
        AccountBalanceResponseDTO accountBalanceResponseDTO = accountMapper.balanceToDto(accounts);

        return EntityModel.of(accountBalanceResponseDTO)
                .add(linkTo(methodOn(AccountController.class).findAccountById(accounts.getId())).withSelfRel());
    }
}
