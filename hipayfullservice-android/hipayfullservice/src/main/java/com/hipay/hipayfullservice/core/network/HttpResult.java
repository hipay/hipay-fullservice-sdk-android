package com.hipay.hipayfullservice.core.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by nfillion on 25/01/16.
 */
public class HttpResult {

    private Integer statusCode;
    private String bodyStream;
    private String errorStream;
    private IOException ioException;

    public HttpResult(int statusCode, String bodyStream, IOException ioException) {

        this.setStatusCode(statusCode);
        this.setBodyStream(bodyStream);
        this.setIoException(ioException);
        //this.setJsonException(jsonException);
    }

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
/*
    public JSONException getJsonException() {
        return jsonException;
    }

    public void setJsonException(JSONException jsonException) {
        this.jsonException = jsonException;
    }
*/

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

