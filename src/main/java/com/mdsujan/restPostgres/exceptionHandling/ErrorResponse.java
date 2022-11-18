package com.mdsujan.restPostgres.exceptionHandling;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    // a response to be sent in case error takes place
    // to provide useful information about the occurrence of said error
    private final int status;
    private final String message;
}