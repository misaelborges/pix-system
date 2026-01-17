package com.misael.pix_sistem.domain.service;

import com.misael.pix_sistem.api.dto.request.PixKeysRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountPixKeyResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixKeysResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixResponseResumoDTO;
import com.misael.pix_sistem.domain.model.PixKeys;

import java.util.List;

public interface PixKeysService {

    AccountPixKeyResponseDTO listPixKey(Long id);
    PixKeysResponseDTO createPixKey(PixKeysRequestDTO pixKeysRequestDTO);
    void deletePixKey(Long pixKeyId);
    PixResponseResumoDTO validatePixKey(String pixKey);

}
