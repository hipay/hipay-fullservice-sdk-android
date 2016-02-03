package com.hipay.hipayfullservice.core.serialization.interfaces;

import com.hipay.hipayfullservice.core.requests.AbstractRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public abstract class AbstractRequestSerialization implements ISerialization {

    protected AbstractRequest request;

    public abstract Map<String, String> getSerializedRequest();

    public AbstractRequest getRequest() {
        return request;
    }

    public void setRequest(AbstractRequest request) {
        this.request = request;
    }
}