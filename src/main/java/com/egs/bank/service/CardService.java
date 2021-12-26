package com.egs.bank.service;

import com.egs.bank.domain.Card;
import com.egs.bank.enums.Currency;
import com.egs.bank.model.dto.BalanceDto;
import com.egs.bank.model.dto.CardDto;
import com.egs.bank.model.request.CardRequest;

import java.math.BigDecimal;
import java.util.Map;

public interface CardService {

    CardDto registerCard(CardRequest cardRequest);

    BalanceDto getBalance(Long id);
}
