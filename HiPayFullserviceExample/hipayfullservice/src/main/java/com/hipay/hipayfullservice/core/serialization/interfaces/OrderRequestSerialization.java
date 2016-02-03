package com.hipay.hipayfullservice.core.serialization.interfaces;

import com.hipay.hipayfullservice.core.requests.order.OrderRequest;

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
}
