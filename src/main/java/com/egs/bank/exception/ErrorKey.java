package com.egs.bank.exception;

import org.springframework.http.HttpStatus;

public enum ErrorKey {

    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND),
    NOT_ENOUGH_BALANCE(HttpStatus.FORBIDDEN),
    BAD_CREDENTIALS(HttpStatus.FORBIDDEN),
    CARD_IS_BLOCKED(HttpStatus.FORBIDDEN),
    CARD_IS_EXPIRED(HttpStatus.FORBIDDEN),
    FINGERPRINT_ALREADY_EXISTS(HttpStatus.CONFLICT);

    private HttpStatus status;

    ErrorKey(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
