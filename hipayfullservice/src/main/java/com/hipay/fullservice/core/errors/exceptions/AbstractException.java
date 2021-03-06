package com.hipay.fullservice.core.errors.exceptions;

/**
 * Created by HiPay on 05/04/16.
 */

public abstract class AbstractException extends Exception {

    private Integer statusCode;

    public AbstractException(String message, Integer statusCode, Throwable throwable) {
        super(message, throwable);
        this.statusCode = statusCode;
    }

    public AbstractException(String localizedMessage) {
        super(localizedMessage, null);
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
