package com.egs.bank.controller;

import com.egs.bank.enums.Currency;
import com.egs.bank.model.dto.BalanceDto;
import com.egs.bank.model.dto.CardDto;
import com.egs.bank.model.request.CardRequest;
import com.egs.bank.model.response.EGSResponse;
import com.egs.bank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @ResponseBody
    @PostMapping
    EGSResponse<CardDto> registerCard(@RequestBody CardRequest cardRequest) {
        CardDto response = cardService.registerCard(cardRequest);
        return new EGSResponse(response, HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping(value = "/{id}/balance")
    EGSResponse<BalanceDto> getBalance(@PathVariable Long id) {
        BalanceDto balance = cardService.getBalance(id);

        return new EGSResponse(balance, HttpStatus.OK);
    }
}
