package ru.freelib.exception;

import org.springframework.http.HttpStatus;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }

    public org.springframework.http.HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
