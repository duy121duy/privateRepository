package com.example.aerospikedemo.exception;


import org.springframework.web.multipart.MultipartException;

public class GlobalException extends MultipartException {
    private String message;

    public GlobalException(String message) {
        super(message);
        this.message = message;
    }
}
