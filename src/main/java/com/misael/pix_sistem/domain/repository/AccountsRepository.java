package com.misael.pix_sistem.domain.repository;

import com.misael.pix_sistem.domain.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    boolean existsByEmail(String email);
}
