package com.hipay.hipayfullservice.core.network;

import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nfillion on 25/01/16.
 */
public class HttpResponse {

    private int statusCode;
    private String responseMessage;
    private InputStream bodyStream;
    private InputStream errorStream;
    private IOException ioException;

    public HttpResponse (int statusCode, InputStream bodyStream, IOException ioException) {

        this.setStatusCode(statusCode);
        this.setBodyStream(bodyStream);
        this.setIoException(ioException);
        //this.setJsonException(jsonException);
    }

    public HttpResponse() {}

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
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

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public InputStream getBodyStream() {
        return bodyStream;
    }

    public void setBodyStream(InputStream bodyStream) {
        this.bodyStream = bodyStream;
    }

    public InputStream getErrorStream() {
        return errorStream;
    }

    public void setErrorStream(InputStream errorStream) {
        this.errorStream = errorStream;
    }

}

