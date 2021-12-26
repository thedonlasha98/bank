package com.egs.bank.service;

import com.egs.bank.domain.Account;
import com.egs.bank.domain.Transaction;
import com.egs.bank.enums.TransactionType;
import com.egs.bank.exception.EGSException;
import com.egs.bank.exception.ErrorKey;
import com.egs.bank.model.request.CashRequest;
import com.egs.bank.repository.AccountRepository;
import com.egs.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final CardService cardService;

    private final AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, CardService cardService, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.cardService = cardService;
        this.accountRepository = accountRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cashDeposit(CashRequest cashRequest) {
        Account account = accountRepository.findByCardIdAndCurrency(cashRequest.getCardId(), cashRequest.getCurrency());
        BigDecimal balance = account.getBalance();

        createTransaction(TransactionType.IN, account, cashRequest);

        balance = balance.add(cashRequest.getAmount());
        account.setBalance(balance);

        accountRepository.save(account);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cashWithdrawal(CashRequest cashRequest) {
        Account account = accountRepository.findByCardIdAndCurrency(cashRequest.getCardId(), cashRequest.getCurrency());
        BigDecimal balance = account.getBalance();
        if (balance.compareTo(cashRequest.getAmount()) < 0) {
            throw new EGSException(ErrorKey.NOT_ENOUGH_BALANCE);
        }
        createTransaction(TransactionType.OUT, account, cashRequest);

        balance = balance.subtract(cashRequest.getAmount());
        account.setBalance(balance);

        accountRepository.save(account);
    }

    private void createTransaction(TransactionType type, Account account, CashRequest cashRequest) {

        Transaction transaction = Transaction.builder()
                .transactionType(type)
                .transactionTime(LocalDateTime.now())
                .account(account)
                .currency(cashRequest.getCurrency())
                .amount(cashRequest.getAmount())
                .build();

        transactionRepository.save(transaction);
    }
}
