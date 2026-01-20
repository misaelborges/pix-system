package com.misael.pix_sistem.api.assemblers;

import com.misael.pix_sistem.api.controller.AccountController;
import com.misael.pix_sistem.api.dto.response.AccountBalanceResponseDTO;
import com.misael.pix_sistem.api.dto.response.AccountsResponseDTO;
import com.misael.pix_sistem.core.config.mapper.AccountMapper;
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

    public EntityModel<AccountsResponseDTO> toModel(AccountsResponseDTO accountsResponseDTO) {

        return EntityModel.of(accountsResponseDTO)
                .add(linkTo(methodOn(AccountController.class).findAccountById(accountsResponseDTO.id())).withSelfRel())
                .add(linkTo(AccountController.class).withRel("accounts"));
    }

    public EntityModel<AccountBalanceResponseDTO> balanceToDto(AccountBalanceResponseDTO balanceResponseDTO) {
        return EntityModel.of(balanceResponseDTO)
                .add(linkTo(methodOn(AccountController.class).findAccountById(balanceResponseDTO.id())).withSelfRel());
    }
}
