package com.egs.bank.controller;

import com.egs.bank.exception.EGSException;
import com.egs.bank.model.dto.CheckCardDto;
import com.egs.bank.model.request.FingerprintRequest;
import com.egs.bank.service.AuthService;
import com.egs.bank.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/card")
    ResponseEntity<CheckCardDto> checkCard(@RequestParam(name = "card_no") String cardNo,
                                           @RequestParam(name = "cvv") String cvv,
                                           @RequestParam(name = "exp_date") String expDate) {
//        if (1==1){
//            throw new EGSException("test",HttpStatus.BAD_REQUEST);
//        }
        Utils.validateCard(cardNo, cvv, expDate);

        CheckCardDto response = authService.checkCard(cardNo, cvv, expDate);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "/card/{id}/validate-credential")
    ResponseEntity<CheckCardDto> validateCredentials(@PathVariable Long id,
                                                     @RequestParam String pin,
                                                     @RequestParam(required = false) String fingerprint) {
        Utils.validatePin(pin);

        CheckCardDto response = authService.validateCardByPinAndFingerprint(id, pin, fingerprint);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PatchMapping(value = "/card{id}/set-fingerprint")
    ResponseEntity<Void> setFingerprint(@PathVariable Long id, FingerprintRequest request) {
        authService.setFingerprint(id, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
