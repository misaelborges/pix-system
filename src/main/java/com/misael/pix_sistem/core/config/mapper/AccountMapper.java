package com.misael.pix_sistem.core.config.mapper;

import com.misael.pix_sistem.api.dto.request.AccountUpdateRequestDTO;
import com.misael.pix_sistem.api.dto.request.AccountsRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountBalanceResponseDTO;
import com.misael.pix_sistem.api.dto.response.AccountsResponseDTO;
import com.misael.pix_sistem.domain.model.Accounts;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Accounts toEntity(AccountsRequestDTO accountsRequestDTO);
    AccountsResponseDTO toDto(Accounts accounts);
    AccountBalanceResponseDTO balanceToDto(Accounts accounts);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AccountUpdateRequestDTO dto, @MappingTarget Accounts account);
}
