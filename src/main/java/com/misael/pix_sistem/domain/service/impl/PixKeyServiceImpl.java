package com.misael.pix_sistem.domain.service.impl;

import com.misael.pix_sistem.domain.exceptions.AccountNotFoundException;
import com.misael.pix_sistem.domain.model.Accounts;
import com.misael.pix_sistem.domain.model.PixKeys;
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

    public PixKeyServiceImpl(PixKeysRepository pixKeysRepository, AccountsRepository accountsRepository) {
        this.pixKeysRepository = pixKeysRepository;
        this.accountsRepository = accountsRepository;
    }

    @Override
    public List<PixKeys> listPixKey(Long id) {
        return pixKeysRepository.findByAccountsIdIdAndActiveTrue(id);
    }

    @Override
    @Transactional
    public PixKeys createPixKey(PixKeys pixKeys) {

        Accounts accounts = accountsRepository.findById(pixKeys.getAccountsId().getId())
                .orElseThrow(() -> new AccountNotFoundException("A conta não foi encontrada"));

        if (pixKeysRepository.countByAccountsIdIdAndActiveTrue(pixKeys.getAccountsId().getId()) >= 5) {
            throw new RuntimeException("Já existem 5 chaves cadastrada na sua conta");
        }

        if (pixKeysRepository.existsByAccountsIdIdAndKeyTypeAndActiveTrue(pixKeys.getAccountsId().getId(), pixKeys.getKeyType())) {
            throw new RuntimeException(String.format("Já existe uma chave pix do tipo %s cadastrada no sistema", pixKeys.getKeyType()));
        }

        validatorKeyPix(pixKeys.getKeyType(), pixKeys.getKeyValue());
        if (pixKeys.getKeyType().equals("Random")) {
            pixKeys.setKeyValue(generateRandomKey().toString());
        }
        pixKeys.setAccountsId(accounts);

        return pixKeysRepository.save(pixKeys);
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
