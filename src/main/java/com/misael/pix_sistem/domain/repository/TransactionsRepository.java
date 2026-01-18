package com.misael.pix_sistem.domain.repository;

import com.misael.pix_sistem.domain.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    @Query("SELECT t FROM transactions t WHERE t.senderId.id = :accountId OR t.receiverId.id = :accountId ORDER BY t.createdAt DESC")
    List<Transactions> findAllByAccountId(@Param("accountId") Long accountId);
}
