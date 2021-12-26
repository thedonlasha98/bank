package com.egs.bank.service;

import com.egs.bank.model.request.CashRequest;

public interface TransactionService {

    void cashDeposit(CashRequest cashRequest);

    void cashWithdrawal(CashRequest cashRequest);
}
