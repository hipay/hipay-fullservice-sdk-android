package com.hipay.hipayfullservice.core.errors.exceptions;

/**
 * Created by nfillion on 05/04/16.
 */
public class HttpException extends AbstractException {

    public HttpException(String message, Integer statusCode, Throwable throwable) {
        super(message, statusCode, throwable);
    }

    public HttpException(Exception exception) {
        super(exception.getLocalizedMessage());
    }
}
