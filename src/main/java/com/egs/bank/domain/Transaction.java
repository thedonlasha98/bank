package com.egs.bank.domain;

import com.egs.bank.enums.Currency;
import com.egs.bank.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @ManyToOne
    Account account;

    @Enumerated(EnumType.STRING)
    TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    Currency currency;

    BigDecimal amount;

    LocalDateTime transactionTime;
}
