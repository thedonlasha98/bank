package com.egs.bank.controller;

import com.egs.bank.exception.EGSException;
import com.egs.bank.model.response.EGSExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler(EGSException.class)
    ResponseEntity<EGSExceptionResponse> handleException(EGSException ex, HttpServletRequest req) {
        log.error("Request: " + req.getRequestURL() + " raised ", ex);
        return new ResponseEntity<>(new EGSExceptionResponse(ex.getMessage()), ex.getStatus());
    }

}
