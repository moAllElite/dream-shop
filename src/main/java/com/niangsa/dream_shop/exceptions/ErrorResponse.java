package com.niangsa.dream_shop.exceptions;



import io.soabase.recordbuilder.core.RecordBuilder;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@RecordBuilder
@Builder
public record ErrorResponse(
        String message,
        HttpStatus httpStatus,
        ZonedDateTime timestamp
) {


}
