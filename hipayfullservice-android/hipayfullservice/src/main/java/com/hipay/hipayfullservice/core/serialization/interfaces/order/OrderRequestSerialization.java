package com.hipay.hipayfullservice.core.serialization.interfaces.order;

import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.utils.Utils;

import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public class OrderRequestSerialization extends OrderRelatedRequestSerialization {

    public OrderRequestSerialization(OrderRequest orderRequest) {

        this.setRequest(orderRequest);
    }

    public Map<String, String> getSerializedRequest() {

        Map<String, String> relatedRequestMap = super.getSerializedRequest();

        OrderRequest orderRequest = (OrderRequest)this.getRequest();

        relatedRequestMap.put("payment_product", orderRequest.getPaymentProductCode());

        return relatedRequestMap;
    }

    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}
