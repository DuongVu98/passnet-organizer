package com.cseiu.passnetorganizer.adapter.advice;

import com.cseiu.passnetorganizer.adapter.rest.BaseController;
import com.cseiu.passnetorganizer.domain.exception.DepartmentAlreadyExistException;
import com.cseiu.passnetorganizer.domain.exception.DepartmentNotFoundException;
import com.cseiu.passnetorganizer.domain.exception.OrganizationNotFoundException;
import com.cseiu.passnetorganizer.domain.exception.StudentNotFoundException;
import com.cseiu.passnetorganizer.domain.view.ErrorView;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorView> handle(MethodArgumentNotValidException exception) {
        var violations = exception.getBindingResult().getAllErrors()
           .stream()
           .map(DefaultMessageSourceResolvable::getDefaultMessage)
           .collect(Collectors.toList());
        return returnBadRequest("Invalid input", violations);
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<ErrorView> handle(OrganizationNotFoundException exception) {
        return returnNotFound(exception.getMessage());
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorView> handle(DepartmentNotFoundException exception) {
        return returnNotFound(exception.getMessage());
    }

    @ExceptionHandler(DepartmentAlreadyExistException.class)
    public ResponseEntity<ErrorView> handle(DepartmentAlreadyExistException exception) {
        return returnNotFound(exception.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorView> handle(StudentNotFoundException exception) {
        return returnNotFound(exception.getMessage());
    }
}
