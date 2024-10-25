package com.ylluberes.kenectlabs.contactchallenge.exception.handler;

import com.ylluberes.kenectlabs.contactchallenge.exception.NotSupportedProviderException;
import com.ylluberes.kenectlabs.contactchallenge.exception.ProviderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NotSupportedProviderException.class})
    public ResponseEntity<Object> handleNotValidProviderException(NotSupportedProviderException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ProviderException.class})
    public ResponseEntity<Object> handleProviderException(ProviderException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
