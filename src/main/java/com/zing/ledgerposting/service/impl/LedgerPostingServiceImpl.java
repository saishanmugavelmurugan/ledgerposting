package com.zing.ledgerposting.service.impl;

import com.google.gson.Gson;
import com.zing.ledgerposting.db.entity.AccountEntity;
import com.zing.ledgerposting.db.entity.LedgerEntryEntity;
import com.zing.ledgerposting.db.repository.AccountRepository;
import com.zing.ledgerposting.db.repository.LedgerEntryRepository;
import com.zing.ledgerposting.enums.TransactionType;
import com.zing.ledgerposting.exception.LedgerNotFoundException;
import com.zing.ledgerposting.mapper.LedgerPostingMapper;
import com.zing.ledgerposting.model.LedgerBalance;
import com.zing.ledgerposting.model.Transaction;
import com.zing.ledgerposting.publish.LedgerPublish;
import com.zing.ledgerposting.service.LedgerPostingService;
import com.zing.ledgerposting.utils.LedgerpostingUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LedgerPostingServiceImpl implements LedgerPostingService {

    private LedgerEntryRepository ledgerEntryRepository;
    private AccountRepository accountRepository;
    private LedgerPostingMapper ledgerPostingMapper;

    private LedgerPublish ledgerPublish;

    private LedgerpostingUtils ledgerpostingUtils;

    public LedgerPostingServiceImpl(LedgerEntryRepository ledgerEntryRepository, AccountRepository accountRepository,
                                    LedgerpostingUtils ledgerpostingUtils, LedgerPostingMapper ledgerPostingMapper, LedgerPublish ledgerPublish){
        this.accountRepository = accountRepository;
        this.ledgerpostingUtils = ledgerpostingUtils;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.ledgerPostingMapper=  ledgerPostingMapper;
        this.ledgerPublish = ledgerPublish;
    }

    /**
     *
     * @param transaction
     * @return
     */
    @Transactional
    public String createLedgerEntry(Transaction transaction) {
        Optional<AccountEntity> optionalAccountEntity = Optional.ofNullable(accountRepository.findByAccountId(transaction.accountId()));
        AccountEntity accountEntity = ledgerpostingUtils.applyTransaction(transaction, optionalAccountEntity);
        LedgerEntryEntity ledgerEntryEntity = ledgerPostingMapper.mapToLedgerEntity(transaction);
        ledgerEntryEntity.setBalance(accountEntity.getBalance());
        ledgerEntryEntity.setBalanceType(accountEntity.getType());
        ledgerEntryRepository.save(ledgerEntryEntity);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                ;
        accountRepository.save(accountEntity);
        ledgerPublish.publishMessage(ledgerEntryEntity);
        return "Ledger posting successfull.";
    }
}
