package com.hipay.hipayfullservice.core.client.interfaces;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.AbstractRequestCallback;
import com.hipay.hipayfullservice.core.requests.AbstractRequest;

/**
 * Created by nfillion on 22/02/16.
 */
public abstract class AbstractReqHandler implements IReqHandler {

    protected AbstractRequest request;
    protected AbstractRequestCallback callback;

    protected AbstractRequest getRequest() {
        return request;
    }

    protected void setRequest(AbstractRequest request) {
        this.request = request;
    }

    public AbstractRequestCallback getCallback() {
        return callback;
    }

    public void setCallback(AbstractRequestCallback callback) {
        this.callback = callback;
    }
}
