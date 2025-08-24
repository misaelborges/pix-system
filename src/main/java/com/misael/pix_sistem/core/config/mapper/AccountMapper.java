package com.misael.pix_sistem.core.config.mapper;

import com.misael.pix_sistem.api.dto.request.AccountRequestDTO;
import com.misael.pix_sistem.api.dto.request.AccountUpdateRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountBalanceResponseDTO;
import com.misael.pix_sistem.api.dto.response.AccountsResponseDTO;
import com.misael.pix_sistem.api.dto.response.AccountsResumeResponseDTO;
import com.misael.pix_sistem.domain.model.Accounts;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Accounts toEntity(AccountRequestDTO accountRequestDTO);
    AccountsResponseDTO toDto(Accounts accounts);
    List<AccountsResumeResponseDTO> toListResponse(List<Accounts> accounts);
    AccountBalanceResponseDTO balanceToDto(Accounts accounts);
    Accounts toEntity(AccountUpdateRequestDTO accountRequestDTO);
}
