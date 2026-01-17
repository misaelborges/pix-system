package com.misael.pix_sistem.domain.service;

import com.misael.pix_sistem.domain.model.PixKeys;

import java.util.List;

public interface PixKeysService {

    List<PixKeys> listPixKey(Long id);
    PixKeys createPixKey(PixKeys pixKeys);

}
