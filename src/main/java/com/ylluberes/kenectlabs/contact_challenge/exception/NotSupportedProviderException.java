package com.ylluberes.kenectlabs.contact_challenge.exception;

import lombok.Data;

@Data
public class NotSupportedProviderException extends RuntimeException {

    private String message;
    public NotSupportedProviderException(String message) {
        this.message = message;
    }
}
