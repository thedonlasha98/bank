package com.egs.bank.service;

import com.egs.bank.domain.Card;
import com.egs.bank.exception.EGSException;
import com.egs.bank.exception.ErrorKey;
import com.egs.bank.model.dto.BalanceDto;
import com.egs.bank.model.dto.CardDto;
import com.egs.bank.model.dto.CheckCardDto;
import com.egs.bank.model.request.CardRequest;
import com.egs.bank.model.request.FingerprintRequest;
import com.egs.bank.repository.CardRepository;
import com.egs.bank.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
public class CardServiceImpl implements CardService, AuthService {

    private CardRepository cardRepository;

    private AccountService accountService;

    private PasswordEncoder encoder;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, AccountService accountService, PasswordEncoder encoder) {
        this.cardRepository = cardRepository;
        this.accountService = accountService;
        this.encoder = encoder;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CardDto registerCard(CardRequest cardRequest) {
        String cardNo = Utils.randomNumber(16);
        String pin = Utils.randomNumber(4);
        String cvv = Utils.randomNumber(3);

        Card card = generateCard(cardRequest, cvv, cardNo, pin);
        cardRepository.save(card);
        accountService.createAccount(cardRequest.getCurrencies(), card);

        return CardDto.builder()
                .cardNo(cardNo)
                .pin(pin)
                .cvv(cvv)
                .expDate(card.getExpDate())
                .build();
    }

    @Override
    public void setFingerprint(Long id, FingerprintRequest request) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new EGSException(ErrorKey.CARD_NOT_FOUND));
        if (StringUtils.hasText(card.getFingerprint())) {
            throw new EGSException(ErrorKey.FINGERPRINT_ALREADY_EXISTS);
        }
        card.setFingerprint(request.getFingerprint());

        cardRepository.save(card);
    }

    @Override
    public CheckCardDto checkCard(String cardNo, String cvv, String expDate) {
        Card card = cardRepository.getByCardNoAndExpDate(cardNo, expDate).orElseThrow(() -> new EGSException(ErrorKey.CARD_NOT_FOUND));
        boolean validCvv = encoder.matches(cvv, card.getCvv());
        if (!validCvv) {
            throw new EGSException(ErrorKey.BAD_CREDENTIALS);
        }
        validateCardStatus(card);

        return new CheckCardDto(StringUtils.hasText(card.getFingerprint()), card.getId());
    }

    @Override
    public CheckCardDto validateCardByPinAndFingerprint(Long id, String pin, String fingerprint) {

        Card card = cardRepository.findById(id).orElseThrow(() -> new EGSException(ErrorKey.CARD_NOT_FOUND));
        validateCardStatus(card);
        validateFingerprint(card, fingerprint);
        validatePin(pin, card);
        successLogin(card);
        return new CheckCardDto(StringUtils.hasText(fingerprint), card.getId());
    }

    @Override
    public List<BalanceDto> getBalance(Long id) {
        List<BalanceDto> response = accountService.getBalance(id);

        return response;
    }

    private Card generateCard(CardRequest cardRequest, String cvv, String cardNo, String pin) {

        YearMonth yearMonth = YearMonth.now().plusYears(cardRequest.getTerm());
        String expDate = String.format("%s/%s", yearMonth.getMonthValue(), String.valueOf(yearMonth.getYear()).substring(2));

        return Card.builder()
                .cardHolder(cardRequest.getFirstName().toUpperCase() + " " + cardRequest.getLastName().toUpperCase())
                .attempts(0)
                .isBlocked(0)
                .cardNo(cardNo)
                .pin(encoder.encode(pin))
                .expDate(expDate)
                .cvv(encoder.encode(cvv))
                .build();
    }

    private void validateForBlock(Card card) {
        int failedAttempts = card.getAttempts() + 1;
        if (failedAttempts == 3) {
            card.setIsBlocked(1);
        }
        card.setAttempts(failedAttempts);
        cardRepository.save(card);
    }

    private void successLogin(Card card) {
        if (card.getAttempts() > 0) {
            card.setAttempts(0);
            cardRepository.save(card);
        }
    }

    private boolean isExpiredCard(String expDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
            simpleDateFormat.setLenient(false);
            Date expiry = simpleDateFormat.parse(expDate);
            boolean expired = expiry.before(new Date());

            return expired;
        } catch (ParseException ex) {
            return true;
        }
    }

    private void validatePin(String pin, Card card) {
        boolean validPin = encoder.matches(pin, card.getPin());
        if (!validPin) {
            validateForBlock(card);
            throw new EGSException(ErrorKey.BAD_CREDENTIALS);
        }
    }

    private void validateCardStatus(Card card) {
        if (card.getIsBlocked() == 1) {
            throw new EGSException(ErrorKey.CARD_IS_BLOCKED);
        }
        if (isExpiredCard(card.getExpDate())) {
            throw new EGSException(ErrorKey.CARD_IS_EXPIRED);
        }
    }

    private void validateFingerprint(Card card, String fingerprint) {
        if (StringUtils.hasText(card.getFingerprint())) {
            boolean validFingerprint = StringUtils.hasText(fingerprint) && encoder.matches(fingerprint, card.getFingerprint());
            if (!validFingerprint) {
                validateForBlock(card);
                throw new EGSException(ErrorKey.BAD_CREDENTIALS);
            }
        }
    }
}
