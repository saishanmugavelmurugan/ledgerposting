package com.zing.ledgerposting.utils;

import com.zing.ledgerposting.db.entity.AccountEntity;
import com.zing.ledgerposting.enums.TransactionType;
import com.zing.ledgerposting.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LedgerpostingUtils {
    /**
     * calculate balance and type based on transaction and type.
     * @param transaction
     * @param optionalAccountEntity
     * @return AccountEntity
     */
    public AccountEntity applyTransaction(Transaction transaction,Optional<AccountEntity> optionalAccountEntity) {

        AccountEntity accountEntity;
        if(optionalAccountEntity.isPresent()){
            accountEntity = optionalAccountEntity.get();
            if(accountEntity.getType().equals(transaction.type())){
                accountEntity.setBalance(accountEntity.getBalance()+transaction.amount());
            }else{
                if(accountEntity.getType().equals(TransactionType.DR) && transaction.type().equals(TransactionType.CR)){
                    if(accountEntity.getBalance()>transaction.amount()){
                        accountEntity.setBalance(accountEntity.getBalance()-transaction.amount());
                        accountEntity.setAccountId(transaction.accountId());
                    }else{
                        accountEntity.setType(TransactionType.CR);
                        accountEntity.setBalance(transaction.amount()-accountEntity.getBalance());
                        accountEntity.setAccountId(transaction.accountId());
                    }

                }else {
                    if(accountEntity.getBalance()>transaction.amount()){
                        accountEntity.setBalance(accountEntity.getBalance()-transaction.amount());
                        accountEntity.setAccountId(transaction.accountId());
                    }else{
                        accountEntity.setType(TransactionType.DR);
                        accountEntity.setBalance(transaction.amount()-accountEntity.getBalance());
                        accountEntity.setAccountId(transaction.accountId());
                    }
                }
            }
        }else{
             accountEntity = new AccountEntity();
            accountEntity.setType(transaction.type());
            accountEntity.setBalance(transaction.amount());
            accountEntity.setAccountId(transaction.accountId());
            return accountEntity;
        }
        return accountEntity;
    }


}
