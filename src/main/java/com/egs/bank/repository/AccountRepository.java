package com.egs.bank.repository;

import com.egs.bank.domain.Account;
import com.egs.bank.enums.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account,Long> {
    List<Account> findByCardId(Long id);

    Account findByCardIdAndCurrency(Long cardId, Currency currency);
}
