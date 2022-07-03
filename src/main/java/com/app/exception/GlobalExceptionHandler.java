package com.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHandlerExceptionResolver {

    @ExceptionHandler(EntityPersistentException.class)
    public ResponseEntity<ExceptionEntity> entityPersistentException(EntityPersistentException e) {
        return getResponseEntityForException(e.getClass().getName(), e.getMessage(), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionEntity> entityNotFoundException(EntityNotFoundException e) {
        return getResponseEntityForException(e.getClass().getName(), e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DriverAlreadyExists.class)
    public ResponseEntity<ExceptionEntity> driverAlreadyExistsException(DriverAlreadyExists e) {
        return getResponseEntityForException(e.getClass().getName(), e.getMessage(), HttpStatus.FOUND);
    }

    @ExceptionHandler(DriverAvailableException.class)
    public ResponseEntity<ExceptionEntity> driverAvailableException(DriverAvailableException e) {
        return getResponseEntityForException(e.getClass().getName(), e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DriverSignInOutException.class)
    public ResponseEntity<ExceptionEntity> driverAlreadySignedInException(DriverSignInOutException e) {
        return getResponseEntityForException(e.getClass().getName(), e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ExceptionEntity> invalidInputException(InvalidInputException e) {
        return getResponseEntityForException(e.getClass().getName(), e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionEntity> invalidRequestException(InvalidRequestException e) {
        return getResponseEntityForException(e.getClass().getName(), e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidOnboardingStepException.class)
    public ResponseEntity<ExceptionEntity> invalidSOnboardingStepException(InvalidOnboardingStepException e) {
        return getResponseEntityForException(e.getClass().getName(), e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ExceptionEntity> internalServerException(InternalServerException e) {
        return getResponseEntityForException(e.getClass().getName(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<ExceptionEntity> getResponseEntityForException(String name, String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ExceptionEntity(name,message), httpStatus);
    }
}
