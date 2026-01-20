package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.api.dto.request.PixKeysRequestDTO;
import com.misael.pix_sistem.api.dto.response.AccountPixKeyResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixKeysResponseDTO;
import com.misael.pix_sistem.api.dto.response.PixResponseResumoDTO;
import com.misael.pix_sistem.core.config.mapper.PixKeyMapper;
import com.misael.pix_sistem.domain.exceptions.AccountNotFoundException;
import com.misael.pix_sistem.domain.exceptions.MaxPixKeysLimitException;
import com.misael.pix_sistem.domain.exceptions.PixKeyAlreadyExistsException;
import com.misael.pix_sistem.domain.exceptions.PixKeyNotFoundException;
import com.misael.pix_sistem.domain.model.Account;
import com.misael.pix_sistem.domain.model.PixKey;
import com.misael.pix_sistem.domain.repository.AccountsRepository;
import com.misael.pix_sistem.domain.repository.PixKeysRepository;
import com.misael.pix_sistem.domain.service.PixKeysService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PixKeyServiceImpl implements PixKeysService {

    private final PixKeysRepository pixKeysRepository;
    private final AccountsRepository accountsRepository;
    private final PixKeyMapper pixKeyMapper;

    public PixKeyServiceImpl(PixKeysRepository pixKeysRepository,
                             AccountsRepository accountsRepository,
                             PixKeyMapper pixKeyMapper) {

        this.pixKeysRepository = pixKeysRepository;
        this.accountsRepository = accountsRepository;
        this.pixKeyMapper = pixKeyMapper;
    }

    @Override
    public AccountPixKeyResponseDTO listPixKey(Long id) {
        List<PixKey> pixKeyList = pixKeysRepository.findByAccountsIdIdAndActiveTrue(id);
        return pixKeyMapper.toAccountPixKeyResponseDTO(id, pixKeyList);
    }

    @Override
    @Transactional
    public PixKeysResponseDTO createPixKey(PixKeysRequestDTO pixKeysRequestDTO) {

        Account account = accountsRepository.findById(pixKeysRequestDTO.accountsId())
                .orElseThrow(() -> new AccountNotFoundException("A conta não foi encontrada"));

        if (pixKeysRepository.countByAccountsIdIdAndActiveTrue(account.getId()) >= 5) {
            throw new MaxPixKeysLimitException();
        }

        if (pixKeysRepository.existsByAccountsIdIdAndKeyTypeAndActiveTrue(account.getId(), pixKeysRequestDTO.keyType())) {
            throw new PixKeyAlreadyExistsException();
        }

        validatorKeyPix(pixKeysRequestDTO.keyType(), pixKeysRequestDTO.keyValue());
        PixKey pixKey = pixKeyMapper.toEntity(pixKeysRequestDTO);

        if (pixKeysRequestDTO.keyType().equals("Random")) {
            pixKey.setKeyValue(generateRandomKey().toString());
        }
        pixKey.setAccountId(account);
        pixKey = pixKeysRepository.save(pixKey);
        return pixKeyMapper.toResponseDTO(pixKey);
    }

    @Override
    @Transactional
    public void deletePixKey(Long pixKeyId) {
        PixKey pixKey = pixKeysRepository.findById(pixKeyId).orElseThrow(PixKeyNotFoundException::new);
        pixKey.setActive(false);
    }

    @Override
    public PixResponseResumoDTO validatePixKey(String pixKey) {
        PixKey pixKeys = pixKeysRepository.findByKeyValueAndActiveTrue(pixKey);
        return pixKeyMapper.toResponseResumoDTO(pixKeys);
    }

    private void validatorKeyPix(String keyType, String keyValue) {
        if (keyType.equals("CPF")) {
            validateCpf(keyValue);
        } else if (keyType.equals("CNPJ")) {
            validateCnpj(keyValue);
        } else if (keyType.equals("Email")) {
            validateEmail(keyValue);
        } else if (keyType.equals("Phone")) {
            validatePhone(keyValue);
        }
    }

    private void validateCpf(String cpf) {
        if (!cpf.trim().matches("^\\d{11}$")) {
            throw new RuntimeException("O CPF esta inválido");
        }
    }

    private void validateCnpj(String cnpj) {
        if (!cnpj.matches("^\\d{14}$")) {
            throw new RuntimeException("O CNPJ esta inválido");
        }
    }

    private void validateEmail(String email) {
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new RuntimeException("O Email esta inválido");
        }
    }

    private void validatePhone(String phone) {
        if (!phone.matches("^\\d{10,11}$")) {
            throw new RuntimeException("O Telefone esta inválido");
        }
    }

    private UUID generateRandomKey() {
        return UUID.randomUUID();
    }



}
