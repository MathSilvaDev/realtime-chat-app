package com.matheus.realtimechat.infrastructure.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ApiError(
        int error,
        String errorMessage,
        Object message,
        Instant timeStamp
) {
    ApiError(HttpStatus statusCode, Object message){
        this(
                statusCode.value(),
                statusCode.getReasonPhrase(),
                message,
                Instant.now()
        );
    }
}
