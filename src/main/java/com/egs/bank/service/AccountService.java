package com.egs.bank.service;

import com.egs.bank.domain.Account;
import com.egs.bank.domain.Card;
import com.egs.bank.enums.Currency;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface AccountService {

    void createAccount(Set<Currency> currencies, Card card);

    Map<Currency, BigDecimal> getBalance(Long id);

    Account getAccount(Long cardId, Currency currency);
}
