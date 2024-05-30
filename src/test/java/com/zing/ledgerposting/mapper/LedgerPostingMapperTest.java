package com.zing.ledgerposting.mapper;

import com.zing.ledgerposting.db.entity.LedgerEntryEntity;
import com.zing.ledgerposting.enums.TransactionType;
import com.zing.ledgerposting.model.Transaction;
import com.zing.ledgerposting.utils.LedgerpostingUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(SpringExtension.class)
public class LedgerPostingMapperTest {
    @InjectMocks
    LedgerPostingMapper ledgerPostingMapper;

    @Test
    void mapToLedgerEntity_whenVaildDataGiven_thenReturnLeggerEntity(){
        Transaction transaction = new Transaction("235325", TransactionType.DR,Double.valueOf("10"), LocalDateTime.parse("2024-05-30T00:00:00"));
        LedgerEntryEntity ledgerEntryEntity=ledgerPostingMapper.mapToLedgerEntity(transaction);
        assertNotNull(ledgerEntryEntity);
        Assertions.assertEquals(transaction.amount(), ledgerEntryEntity.getAmount());
    }
}
