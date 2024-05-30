package com.zing.ledgerposting.db.entity;

import com.zing.ledgerposting.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@ToString
@Entity
public class LedgerEntryEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id")
    private UUID id;

    private String accountId;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private Double amount;
    private LocalDateTime timestamp;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private TransactionType balanceType;

}
