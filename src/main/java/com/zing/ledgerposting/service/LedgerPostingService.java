package com.zing.ledgerposting.service;

import com.zing.ledgerposting.db.entity.LedgerEntryEntity;
import com.zing.ledgerposting.model.LedgerBalance;
import com.zing.ledgerposting.model.Transaction;

import java.util.List;

public interface LedgerPostingService {
    String createLedgerEntry(Transaction transaction);

}
