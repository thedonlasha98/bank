package com.egs.bank.controller;

import com.egs.bank.exception.EGSException;
import com.egs.bank.model.response.EGSExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler(EGSException.class)
    ResponseEntity<EGSExceptionResponse> handleException(EGSException ex) {
        log.error("exception:", ex);
        return new ResponseEntity<>(new EGSExceptionResponse(ex.getMessage()),ex.getStatus());
    }

}
