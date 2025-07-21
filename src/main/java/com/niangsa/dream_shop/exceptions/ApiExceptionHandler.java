package com.niangsa.dream_shop.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    // constantes
    private final ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
    private final static HttpStatus NOT_FOUND=  HttpStatus.NOT_FOUND;
    private final static HttpStatus FOUND=  HttpStatus.FOUND;

    /**
     * throw an error while the entity not exist
     * @param e EntityNotFoundException
     * @return Response of ErrorResponse
     */
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNoEntityFoundException(EntityNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder().message(e.getMessage())
                .httpStatus(NOT_FOUND)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z"))).build();

        return new ResponseEntity<>(errorResponse,NOT_FOUND);
    }


    /**
     * throws an error while try to persist an   entity  already exist
     * @param e EntityExistException
     * @return R
     */
    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExistException(EntityExistsException e) {
        ErrorResponse errorResponse = ErrorResponse.builder().message(e.getMessage())
        .httpStatus(HttpStatus.FOUND).timestamp(now).build();
        return new  ResponseEntity<>(errorResponse, FOUND);
    }


    /***
     *
     * @param e UsernameNotFound
     * @return response with Entity Response
     */
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatus(NOT_FOUND)
                .timestamp(now)
                .message(e.getMessage())
                .build();
        return  new ResponseEntity<>(errorResponse,NOT_FOUND);
    }

}
