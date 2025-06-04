package com.niangsa.dream_shop.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNoEntityFoundException(EntityNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder().message(e.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z"))).build();

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExistException(EntityExistsException e) {
        ErrorResponse errorResponse = ErrorResponse.builder().message(e.getMessage())
        .httpStatus(HttpStatus.FOUND).timestamp(ZonedDateTime.now(ZoneId.of("Z"))).build();
        return new  ResponseEntity<>(
                errorResponse,
               HttpStatus.FOUND
        );
    }

}
