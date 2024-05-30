package com.zing.ledgerposting.mapper;

import com.zing.ledgerposting.db.entity.LedgerEntryEntity;
import com.zing.ledgerposting.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class LedgerPostingMapper {
    /**
     * Map to Ledger Entity
     * @param transaction
     * @return LedgerEntryEntity
     */
    public LedgerEntryEntity mapToLedgerEntity(Transaction transaction){
        LedgerEntryEntity ledgerEntryEntity = new LedgerEntryEntity();
        ledgerEntryEntity.setAccountId(transaction.accountId());
        ledgerEntryEntity.setType(transaction.type());
        ledgerEntryEntity.setAmount(transaction.amount());
        ledgerEntryEntity.setTimestamp(transaction.timestamp());
        return ledgerEntryEntity;
    }


}
