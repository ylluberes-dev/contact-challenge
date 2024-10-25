package com.ylluberes.kenectlabs.contact_challenge.exception.handler;

import com.ylluberes.kenectlabs.contact_challenge.exception.NotSupportedProviderException;
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
}
