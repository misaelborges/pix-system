package com.misael.pix_sistem.domain.repository;

import com.misael.pix_sistem.domain.model.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PixKeysRepository extends JpaRepository<PixKey, Long> {

    List<PixKey> findByAccountsIdIdAndActiveTrue(Long accountId);

    // Verificar se já existe chave do tipo na conta
    boolean existsByAccountsIdIdAndKeyTypeAndActiveTrue(Long accountId, String keyType);

    // Contar chaves ativas da conta
    int countByAccountsIdIdAndActiveTrue(Long accountId);

    PixKey findByKeyValueAndActiveTrue(String keyValue);
}
