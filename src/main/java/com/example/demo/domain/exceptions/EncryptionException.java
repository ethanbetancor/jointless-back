package com.example.demo.domain.exceptions;

public class EncryptionException extends RuntimeException {
    public EncryptionException(String message) {
        super(message);
    }
}
