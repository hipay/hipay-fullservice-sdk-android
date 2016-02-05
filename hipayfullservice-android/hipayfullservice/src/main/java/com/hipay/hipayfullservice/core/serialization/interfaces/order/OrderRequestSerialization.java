package com.hipay.hipayfullservice.core.serialization.interfaces.order;

import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public class OrderRequestSerialization extends OrderRelatedRequestSerialization {

    public OrderRequestSerialization(OrderRequest orderRequest) {

        this.setRequest(orderRequest);
    }

    public Map<String, String> getSerializedRequest() {

        Map<String, String> retMap = new HashMap<>();

        Map<String, String> relatedRequestMap = super.getSerializedRequest();

        //TODO merge maps

        return retMap;
    }

    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}
