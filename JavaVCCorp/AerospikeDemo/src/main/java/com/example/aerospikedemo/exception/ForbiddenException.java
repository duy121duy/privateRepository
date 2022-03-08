package com.example.aerospikedemo.exception;

public class ForbiddenException extends RuntimeException {

    private String message;

    public ForbiddenException(String message) {
        this.message = message;
    }
}