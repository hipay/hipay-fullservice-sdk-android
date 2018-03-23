package com.hipay.fullservice.core.utils.enums;

/**
 * Created by nfillion on 23/03/2018.
 */
public enum HttpMethod {

    GET("GET"),
    POST("POST");

    protected final String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public String getStringValue() {
        return this.method;
    }
}
