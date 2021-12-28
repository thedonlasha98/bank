package com.egs.bank.controller;

import com.egs.bank.model.request.CashRequest;
import com.egs.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cash")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/deposit")
    ResponseEntity<Void> cashDeposit(@RequestBody CashRequest cashRequest) {
        transactionService.cashDeposit(cashRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/withdrawal")
    ResponseEntity<Void> cashWithdrawal(@RequestBody CashRequest cashRequest) {
        transactionService.cashWithdrawal(cashRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
