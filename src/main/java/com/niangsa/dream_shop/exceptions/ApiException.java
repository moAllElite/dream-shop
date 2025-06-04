package com.niangsa.dream_shop.exceptions;


import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Builder
public record ApiException(
        String message,
        HttpStatus httpStatus,
        ZonedDateTime timestamp
) {
}
