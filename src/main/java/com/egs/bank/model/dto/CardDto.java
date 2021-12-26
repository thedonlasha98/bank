package com.egs.bank.model.dto;

import com.egs.bank.domain.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDto {

    String cardNo;

    String cvv;

    String expDate;

    String pin;

    public static CardDto toDto(Card card){

        return CardDto.builder()
                .cardNo(card.getCardNo())
                .pin(card.getPin())
                .build();
    }
}
