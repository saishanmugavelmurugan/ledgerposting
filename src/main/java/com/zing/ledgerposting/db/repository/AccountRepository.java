package com.zing.ledgerposting.db.repository;

import com.zing.ledgerposting.db.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    public AccountEntity findByAccountId(String accountId);
}
