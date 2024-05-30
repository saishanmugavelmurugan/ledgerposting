package com.zing.ledgerposting.utils;

import com.zing.ledgerposting.db.entity.AccountEntity;
import com.zing.ledgerposting.enums.TransactionType;
import com.zing.ledgerposting.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith(SpringExtension.class)
public class LedgerpostingUtilsTest {

    @InjectMocks
    LedgerpostingUtils ledgerpostingUtils;

    @Test
    public void applyTransaction_whenValidLedgerGiven_thenReturnCreatedSuccessfullLedger()
            throws Exception {
        //given
        Transaction transaction = new Transaction("25235", TransactionType.DR, Double.valueOf(100), LocalDateTime.parse("2024-05-28T22:22:22"));
        AccountEntity accountEntityMock = new AccountEntity();
        accountEntityMock.setBalance(Double.valueOf(1000));
        accountEntityMock.setType(TransactionType.DR);
        accountEntityMock.setAccountId("25235");
        Optional<AccountEntity> optionalAccountEntity = Optional.of(accountEntityMock);
        //when
        AccountEntity accountEntity = ledgerpostingUtils.applyTransaction(transaction, optionalAccountEntity);


        assertNotNull(accountEntity);
        assertEquals(1100, accountEntity.getBalance());
        assertEquals(transaction.type(), accountEntity.getType());
        assertEquals(transaction.accountId(), accountEntity.getAccountId());

    }

    @Test
    public void applyTransaction_whenValidLedgerWithNewAccountGiven_thenReturnCreatedSuccessfullLedger()
            throws Exception {
        //given
        Transaction transaction = new Transaction("25235", TransactionType.DR, Double.valueOf(100), LocalDateTime.parse("2024-05-28T22:22:22"));
        AccountEntity accountEntityMock = new AccountEntity();
        Optional<AccountEntity> optionalAccountEntity = Optional.ofNullable(null);
        //when
        AccountEntity accountEntity = ledgerpostingUtils.applyTransaction(transaction, optionalAccountEntity);


        assertNotNull(accountEntity);
        assertEquals(transaction.amount(), accountEntity.getBalance());
        assertEquals(transaction.type(), accountEntity.getType());
        assertEquals(transaction.accountId(), accountEntity.getAccountId());

    }

}
