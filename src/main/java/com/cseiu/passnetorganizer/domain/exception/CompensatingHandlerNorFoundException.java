package com.cseiu.passnetorganizer.domain.exception;

public class CompensatingHandlerNorFoundException extends RuntimeException {
    public CompensatingHandlerNorFoundException(String message) {
        super(message);
    }

    public CompensatingHandlerNorFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
