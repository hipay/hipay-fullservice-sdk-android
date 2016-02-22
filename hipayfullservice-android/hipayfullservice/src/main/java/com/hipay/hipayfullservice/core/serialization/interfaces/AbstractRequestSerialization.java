package com.hipay.hipayfullservice.core.serialization.interfaces;

import com.hipay.hipayfullservice.core.requests.AbstractRequest;

import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public abstract class AbstractRequestSerialization implements ISerialization {

    protected AbstractRequest request;

    public abstract Map<String, String> getSerializedRequest();

    public abstract String getQueryString();

    protected AbstractRequest getRequest() {
        return request;
    }

    protected void setRequest(AbstractRequest request) {
        this.request = request;
    }
}