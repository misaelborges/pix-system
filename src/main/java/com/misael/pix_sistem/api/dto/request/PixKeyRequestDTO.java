package com.misael.pix_sistem.api.dto.request;

public record PixKeyRequestDTO(Long accountsId,
                               String keyValue,
                               String keyType
) {
}
