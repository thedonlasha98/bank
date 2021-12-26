package com.egs.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    String cardNo;

    String cvv;

    String expDate;

    String cardHolder;

    String pin;

    String fingerprint;

    int attempts;

    int isBlocked;

}
