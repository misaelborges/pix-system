package com.misael.pix_sistem.core.config.mapper;

import com.misael.pix_sistem.api.dto.request.PixKeyRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountPixKeyResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixKeyResponseDTO;
import com.misael.pix_sistem.domain.model.Accounts;
import com.misael.pix_sistem.domain.model.PixKeys;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PixKeyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "accountsId", target = "accountsId")
    PixKeys toEntity(PixKeyRequestDTO pixKeyRequestDTO);

    default Accounts map(Long accountId) {
        if (accountId == null) {
            return null;
        }
        Accounts account = new Accounts();
        account.setId(accountId);
        return account;
    }

    List<PixKeyResponseDTO> toResponseDTOList(List<PixKeys> entities);

    default AccountPixKeyResponseDTO toAccountPixKeyResponseDTO(
            Long accountId,
            List<PixKeys> pixKeys
    ) {
        return new AccountPixKeyResponseDTO(
                accountId,
                toResponseDTOList(pixKeys)
        );
    }

    PixKeyResponseDTO toResponseDTO(PixKeys pixKeys);
}
