package com.egs.bank.repository;

import com.egs.bank.domain.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card,Long> {

    Optional<Card> getByCardNo(String cardNo);

    Optional<Card> getByCardNoAndCvvAndExpDate(String cardNo, Integer cvv, String expDate);

    Optional<Card> getByCardNoAndExpDate(String cardNo, String expDate);
}
