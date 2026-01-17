package com.misael.pix_sistem.api.dto.response;

import java.util.List;

public record AccountPixKeyResponseDTO(Long accountId,
                                       List<PixKeysResponseDTO> pixKeys) {
}
