package com.example.ingressbookstore.exception;

public class EmailExitsException extends RuntimeException{
    public EmailExitsException(String message) {
        super(message);
    }
}
