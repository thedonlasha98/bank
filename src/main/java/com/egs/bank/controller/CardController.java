package com.egs.bank.controller;

import com.egs.bank.model.dto.BalanceDto;
import com.egs.bank.model.dto.CardDto;
import com.egs.bank.model.request.CardRequest;
import com.egs.bank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    ResponseEntity<CardDto> registerCard(@RequestBody CardRequest cardRequest) {
        CardDto response = cardService.registerCard(cardRequest);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/balance")
    ResponseEntity<List<BalanceDto>> getBalance(@PathVariable Long id) {
        List<BalanceDto> balance = cardService.getBalance(id);

        return new ResponseEntity(balance, HttpStatus.CREATED);
    }
}
