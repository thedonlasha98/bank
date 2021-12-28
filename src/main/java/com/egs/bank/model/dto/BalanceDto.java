package com.egs.bank.model.dto;

import com.egs.bank.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BalanceDto {

    Currency currency;

    BigDecimal balance;
}
