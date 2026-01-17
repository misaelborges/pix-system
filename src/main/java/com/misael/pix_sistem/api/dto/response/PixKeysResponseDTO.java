package com.misael.pix_sistem.api.dto.response;

import java.time.OffsetDateTime;

public record PixKeysResponseDTO(Long id,
                                 String keyValue,
                                 String keyType,
                                 boolean active,
                                 OffsetDateTime createdAt) {
}
