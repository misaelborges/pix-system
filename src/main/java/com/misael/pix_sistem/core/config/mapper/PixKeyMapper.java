package com.misael.pix_sistem.core.config.mapper;

import com.misael.pix_sistem.api.dto.request.PixKeysRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountPixKeyResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixKeysResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixResponseResumoDTO;
import com.misael.pix_sistem.domain.model.Account;
import com.misael.pix_sistem.domain.model.PixKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PixKeyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "accountsId", target = "accountId")
    PixKey toEntity(PixKeysRequestDTO pixKeysRequestDTO);

    default Account map(Long accountId) {
        if (accountId == null) {
            return null;
        }
        Account account = new Account();
        account.setId(accountId);
        return account;
    }

    List<PixKeysResponseDTO> toResponseDTOList(List<PixKey> entities);

    default AccountPixKeyResponseDTO toAccountPixKeyResponseDTO(
            Long accountId,
            List<PixKey> pixKeys
    ) {
        return new AccountPixKeyResponseDTO(
                accountId,
                toResponseDTOList(pixKeys)
        );
    }

    PixKeysResponseDTO toResponseDTO(PixKey pixKey);

    @Mapping(source = "accountId.name", target = "nameAccount")
    PixResponseResumoDTO toResponseResumoDTO(PixKey pixKey);
}
