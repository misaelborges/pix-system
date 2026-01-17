package com.misael.pix_sistem.domain.repository;

import com.misael.pix_sistem.domain.model.PixKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PixKeysRepository extends JpaRepository<PixKeys, Long> {

    List<PixKeys> findByAccountsIdIdAndActiveTrue(Long accountId);

    // Verificar se já existe chave do tipo na conta
    boolean existsByAccountsIdIdAndKeyTypeAndActiveTrue(Long accountId, String keyType);

    // Verificar se chave específica já existe (qualquer conta)
    boolean existsByKeyValueAndActiveTrue(String keyValue);

    // Contar chaves ativas da conta
    int countByAccountsIdIdAndActiveTrue(Long accountId);
}
