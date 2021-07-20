package com.cseiu.passnetorganizer.domain.exception;

public class CommandNotCompatibleException extends RuntimeException {
    public CommandNotCompatibleException(String message) {
        super(message);
    }

    public CommandNotCompatibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
