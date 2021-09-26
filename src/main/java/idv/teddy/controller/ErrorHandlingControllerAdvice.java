package idv.teddy.controller;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ValidationErrorResponse onBindException(BindException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ObjectError objectError : e.getAllErrors())
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                error.getViolations().add(new Violation(String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage())));
            } else
                error.getViolations().add(new Violation(objectError.getDefaultMessage()));
        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ValidationErrorResponse onConstraintViolationException(ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            PathImpl path = (PathImpl) constraintViolation.getPropertyPath();
            if (path.getLeafNode().getKind() != ElementKind.PARAMETER) {
                String field = path.getLeafNode().asString();
                error.getViolations().add(new Violation(String.format("%s %s", field, constraintViolation.getMessage())));
            } else
                error.getViolations().add(new Violation(constraintViolation.getMessage()));
        }
        return error;
    }

}
