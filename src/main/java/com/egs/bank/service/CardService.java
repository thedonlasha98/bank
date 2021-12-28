package com.egs.bank.service;

import com.egs.bank.model.dto.BalanceDto;
import com.egs.bank.model.dto.CardDto;
import com.egs.bank.model.request.CardRequest;

import java.util.List;

public interface CardService {

    CardDto registerCard(CardRequest cardRequest);

    List<BalanceDto> getBalance(Long id);
}
