package com.egs.bank.controller;

import com.egs.bank.model.request.CashRequest;
import com.egs.bank.model.response.EGSResponse;
import com.egs.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cash")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/deposit")
    EGSResponse<Void> cashDeposit(@RequestBody CashRequest cashRequest){
        transactionService.cashDeposit(cashRequest);

        return new EGSResponse(HttpStatus.CREATED);
    }

    @PostMapping(value = "/withdrawal")
    EGSResponse<Void> cashWithdrawal(@RequestBody CashRequest cashRequest){
        transactionService.cashWithdrawal(cashRequest);

        return new EGSResponse(HttpStatus.CREATED);
    }
}
