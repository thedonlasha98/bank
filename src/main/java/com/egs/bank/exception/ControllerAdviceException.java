package com.egs.bank.exception;

import com.egs.bank.exception.EGSException;
import com.egs.bank.model.response.EGSExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ControllerAdviceException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EGSException.class)
    ResponseEntity<Object> handleEGSException(EGSException ex, WebRequest req) {
        ex.printStackTrace();
        EGSExceptionResponse response = new EGSExceptionResponse(ex.getMessage());
        return handleExceptionInternal(ex, response, new HttpHeaders(), ex.getStatus(), req);
    }

}
