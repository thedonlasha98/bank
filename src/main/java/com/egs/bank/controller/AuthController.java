package com.egs.bank.controller;

import com.egs.bank.model.dto.CheckCardDto;
import com.egs.bank.model.request.FingerprintRequest;
import com.egs.bank.model.response.EGSResponse;
import com.egs.bank.service.AuthService;
import com.egs.bank.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;

@Validated
@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/card")
    EGSResponse<CheckCardDto> checkCard(@RequestParam(name = "card_no") String cardNo,
                                        @RequestParam(name = "cvv")String cvv,
                                        @RequestParam(name = "exp_date") String expDate) {

        Utils.validateCard(cardNo,cvv,expDate);

        CheckCardDto response = authService.checkCard(cardNo, cvv, expDate);

        return new EGSResponse(response, HttpStatus.OK);
    }

    @GetMapping(path = "/card/{id}/validate-credential")
    EGSResponse<CheckCardDto> validateCredentials(@PathVariable Long id,
                                                  @RequestParam String pin,
                                                  @RequestParam(required = false) String fingerprint) {
        Utils.validatePin(pin);

        CheckCardDto response = authService.validateCardByPinAndFingerprint(id, pin, fingerprint);

        return new EGSResponse(response, HttpStatus.OK);
    }

    @PatchMapping(path = "/card{id}/set-fingerprint")
    EGSResponse<Void> setFingerprint(@PathVariable Long id, FingerprintRequest request){
        authService.setFingerprint(id, request);

        return new EGSResponse<>(HttpStatus.OK);
    }

}
