package com.cseiu.passnetorganizer.domain.exception;

public class StudentCardIdExistedException extends RuntimeException{
    public StudentCardIdExistedException(String message) {
        super(message);
    }

    public StudentCardIdExistedException(String message, Throwable cause) {
        super(message, cause);
    }
}
