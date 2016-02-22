package com.hipay.hipayfullservice.core.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by nfillion on 25/01/16.
 */
public class HttpResult {

    private int statusCode;
    private String responseMessage;
    private InputStream bodyStream;
    private InputStream errorStream;
    private IOException ioException;

    public HttpResult(int statusCode, InputStream bodyStream, IOException ioException) {

        this.setStatusCode(statusCode);
        this.setBodyStream(bodyStream);
        this.setIoException(ioException);
        //this.setJsonException(jsonException);
    }

    public HttpResult() {}

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

    public static String readStream(InputStream is) throws IOException {

        if (is == null) return null;

        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}

