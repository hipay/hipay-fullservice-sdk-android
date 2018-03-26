package com.hipay.fullservice.core.requests.securevault;

import com.hipay.fullservice.core.requests.AbstractRequest;
import com.hipay.fullservice.core.serialization.SerializationFactory;
import com.hipay.fullservice.core.serialization.interfaces.ISerialization;

/**
 * Created by nfillion on 09/03/16.
 */
public class LookupPaymentCardRequest extends AbstractRequest {

    public LookupPaymentCardRequest(String token, String requestId) {

        this.setToken(token);
        this.setRequestId(requestId);
    }

    protected String token;
    protected String requestId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStringParameters() {
        ISerialization mapper = SerializationFactory.newInstance(this);
        return mapper.getQueryString();
    }
}

