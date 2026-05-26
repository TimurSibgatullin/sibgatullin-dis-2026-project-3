package ru.freelib.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends FreeLibException {
    public AccessDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}