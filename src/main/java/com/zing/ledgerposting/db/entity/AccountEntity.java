package com.zing.ledgerposting.db.entity;

import com.zing.ledgerposting.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
public class AccountEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id")
    private UUID id;
    private String accountId;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

}
