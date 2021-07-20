package com.cseiu.passnetorganizer.domain.exception;

public class WrongCommandTypeException extends RuntimeException{
    public WrongCommandTypeException(String message) {
        super(message);
    }

    public WrongCommandTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
