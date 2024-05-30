package com.zing.ledgerposting.service;

import com.google.gson.Gson;
import com.zing.ledgerposting.db.entity.AccountEntity;
import com.zing.ledgerposting.db.entity.LedgerEntryEntity;
import com.zing.ledgerposting.db.repository.AccountRepository;
import com.zing.ledgerposting.db.repository.LedgerEntryRepository;
import com.zing.ledgerposting.enums.TransactionType;
import com.zing.ledgerposting.mapper.LedgerPostingMapper;
import com.zing.ledgerposting.model.Transaction;
import com.zing.ledgerposting.publish.LedgerPublish;
import com.zing.ledgerposting.service.impl.LedgerPostingServiceImpl;
import com.zing.ledgerposting.utils.LedgerpostingUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class LedgerPostingServiceImplTest {

    @InjectMocks
    private LedgerPostingServiceImpl ledgerPostingService;

    @Mock
    private LedgerEntryRepository ledgerEntryRepository;
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Gson gson;
    @Mock
    private LedgerPostingMapper ledgerPostingMapper;

    @Mock
    private LedgerPublish ledgerPublish;

    @Mock
    private LedgerpostingUtils ledgerpostingUtils;

    @Test
    public void createLedgerPosting_whenValidLedgerGiven_thenReturnCreatedSuccessfullLedger()
            throws Exception {
        //given
        Transaction transaction = new Transaction("25235", TransactionType.DR, Double.valueOf(100),LocalDateTime.parse("2024-05-28T22:22:22") );

        when(ledgerPostingMapper.mapToLedgerEntity(any())).thenReturn(new LedgerEntryEntity());
        when(accountRepository.findByAccountId(anyString())).thenReturn(new AccountEntity());
        when(ledgerEntryRepository.save(any())).thenReturn(new LedgerEntryEntity());
        when(accountRepository.save(any())).thenReturn(new AccountEntity());
        doNothing().when(ledgerPublish).publishMessage(any());
        when(ledgerpostingUtils.applyTransaction(any(),any())).thenReturn(new AccountEntity());
        //when
        String result = ledgerPostingService.createLedgerEntry(transaction);

        assertNotNull(result);
        assertEquals(result, "Ledger posting successfull.");
    }
    @Test
    public void createLedgerPosting_whenValidLedgerGivenpublishfailed_thenReturnCreatedSuccessfullandfailureMessage()
            throws Exception {
        //given
        Transaction transaction = new Transaction("25235", TransactionType.DR, Double.valueOf(100),LocalDateTime.parse("2024-05-28T22:22:22") );

        when(ledgerPostingMapper.mapToLedgerEntity(any())).thenReturn(new LedgerEntryEntity());
        when(accountRepository.findByAccountId(anyString())).thenReturn(new AccountEntity());
        when(ledgerEntryRepository.save(any())).thenReturn(new LedgerEntryEntity());
        when(accountRepository.save(any())).thenReturn(new AccountEntity());
        doNothing().when(ledgerPublish).publishMessage(any());
        when(ledgerpostingUtils.applyTransaction(any(),any())).thenReturn(new AccountEntity());
        //when
        String result = ledgerPostingService.createLedgerEntry(transaction);

        assertNotNull(result);
        assertEquals(result, "Ledger posting successfull.");
    }


}
