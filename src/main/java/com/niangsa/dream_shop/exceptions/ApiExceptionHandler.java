package com.niangsa.dream_shop.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ApiException> handleNoEntityFoundException(EntityNotFoundException e) {
        ApiException apiException = ApiException.builder().message(e.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z"))).build();

        return new ResponseEntity<>(apiException,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<ApiException> handleEntityAlreadyExistException(EntityExistsException e) {
        ApiException apiException = ApiException.builder().message(e.getMessage())
        .httpStatus(HttpStatus.FOUND).timestamp(ZonedDateTime.now(ZoneId.of("Z"))).build();
        return new  ResponseEntity<>(
                apiException,
               HttpStatus.FOUND
        );
    }

}
