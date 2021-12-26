package com.egs.bank.service;

import com.egs.bank.domain.Account;
import com.egs.bank.domain.Card;
import com.egs.bank.enums.Currency;
import com.egs.bank.exception.EGSException;
import com.egs.bank.exception.ErrorKey;
import com.egs.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccount(Set<Currency> currencies, Card card){
        Set<Account> accounts = new HashSet<>();

        for (Currency currency : currencies) {
            Account account = Account.builder()
                    .card(card)
                    .currency(currency)
                    .balance(BigDecimal.ZERO)
                    .build();
            accounts.add(account);
        }
        accountRepository.saveAll(accounts);
    }

    @Override
    public Map<Currency, BigDecimal> getBalance(Long id) {
        List<Account> accounts = accountRepository.findByCardId(id);
        if (accounts == null){
            throw new EGSException(ErrorKey.ACCOUNT_NOT_FOUND);
        }
        Map<Currency,BigDecimal> response = new HashMap<>();
        for (Account account : accounts) {
            response.put(account.getCurrency(),account.getBalance());
        }

        return response;
    }

    @Override
    public Account getAccount(Long cardId, Currency currency){

        return accountRepository.findByCardIdAndCurrency(cardId,currency);
    }
}
