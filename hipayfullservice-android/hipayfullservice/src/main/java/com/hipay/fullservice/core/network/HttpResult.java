package com.hipay.fullservice.core.network;

import java.io.IOException;

/**
 * Created by nfillion on 25/01/16.
 */
public class HttpResult {

    private Integer statusCode;
    private String bodyStream;
    private String errorStream;
    private IOException ioException;

    public HttpResult() {}

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public IOException getIoException() {
        return ioException;
    }

    public void setIoException(IOException ioException) {
        this.ioException = ioException;
    }

    public String getBodyStream() {
        return bodyStream;
    }

    public void setBodyStream(String bodyStream) {
        this.bodyStream = bodyStream;
    }

    public String getErrorStream() {
        return errorStream;
    }

    public void setErrorStream(String errorStream) {
        this.errorStream = errorStream;
    }
}

