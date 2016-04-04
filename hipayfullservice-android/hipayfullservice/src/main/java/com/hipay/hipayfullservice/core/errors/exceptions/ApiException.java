package com.hipay.hipayfullservice.core.errors.exceptions;

/**
 * Created by nfillion on 05/04/16.
 */
public class ApiException extends AbstractException {

    public ApiException(String message, Integer statusCode, Throwable throwable) {
        super(message, statusCode, throwable);
    }
}
