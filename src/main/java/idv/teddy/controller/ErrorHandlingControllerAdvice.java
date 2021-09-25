package idv.teddy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ValidationErrorResponse onBindException(BindException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        assert e.getAllErrors().size() == e.getFieldErrors().size();
        for (FieldError fieldError : e.getFieldErrors()) {
            error.getViolations().add(new Violation(String.format("%s %s", fieldError.getField(),  fieldError.getDefaultMessage())));
        }
        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ValidationErrorResponse onConstraintViolationException(ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            error.getViolations().add(new Violation(constraintViolation.getMessage()));
        }
        return error;
    }

}
