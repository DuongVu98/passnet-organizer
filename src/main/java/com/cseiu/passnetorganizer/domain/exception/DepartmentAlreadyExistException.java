package com.cseiu.passnetorganizer.domain.exception;

public class DepartmentAlreadyExistException extends RuntimeException {
    public DepartmentAlreadyExistException(String message) {
        super(message);
    }

    public DepartmentAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
