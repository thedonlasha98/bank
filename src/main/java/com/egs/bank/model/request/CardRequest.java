package com.egs.bank.model.request;

import com.egs.bank.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @Positive
    int term;

    @NotEmpty
    Set<Currency> currencies;
}
