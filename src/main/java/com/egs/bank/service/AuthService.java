package com.egs.bank.service;

import com.egs.bank.model.dto.CheckCardDto;
import com.egs.bank.model.request.FingerprintRequest;

public interface AuthService {

    CheckCardDto checkCard(String cardNo, String cvv, String expDate);

    CheckCardDto validateCardByPinAndFingerprint(Long id, String pin, String fingerprint);

    void setFingerprint(Long id, FingerprintRequest request);

}
