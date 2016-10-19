package com.hipay.fullservice.core.serialization.interfaces.securevault;

import android.os.Bundle;

import com.hipay.fullservice.core.requests.securevault.LookupPaymentCardRequest;
import com.hipay.fullservice.core.serialization.interfaces.AbstractSerialization;
import com.hipay.fullservice.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 09/03/16.
 */
public class LookupPaymentCardRequestSerialization extends AbstractSerialization {

    public LookupPaymentCardRequestSerialization(LookupPaymentCardRequest updatePaymentCardRequest) {

        super(updatePaymentCardRequest);
    }

    public Map<String, String> getSerializedRequest() {

        LookupPaymentCardRequest lookupPaymentCardRequest = (LookupPaymentCardRequest) this.getModel();

        Map<String, String> retMap = new HashMap<>();
        retMap.put("request_id", lookupPaymentCardRequest.getRequestId());
        retMap.put("token", lookupPaymentCardRequest.getToken());

        while (retMap.values().remove(null));

        return retMap;
    }

    @Override
    public Bundle getSerializedBundle() {
        return null;
    }

    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}

