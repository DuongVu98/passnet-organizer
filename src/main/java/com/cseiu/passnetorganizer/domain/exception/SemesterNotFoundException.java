package com.cseiu.passnetorganizer.domain.exception;

public class SemesterNotFoundException extends RuntimeException{
    public SemesterNotFoundException(String message) {
        super(message);
    }

    public SemesterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
