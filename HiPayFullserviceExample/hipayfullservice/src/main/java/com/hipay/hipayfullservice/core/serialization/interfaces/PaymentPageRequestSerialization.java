package com.hipay.hipayfullservice.core.serialization.interfaces;

import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public class PaymentPageRequestSerialization extends OrderRelatedRequestSerialization {

    public PaymentPageRequestSerialization(PaymentPageRequest paymentPageRequest) {

        this.setRequest(paymentPageRequest);
    }

    public Map<String, String> getSerializedRequest() {

        Map<String, String> relatedRequestMap = super.getSerializedRequest();

        PaymentPageRequest paymentPageRequest = (PaymentPageRequest)this.getRequest();

        Map<String, String> retMap = new HashMap<>();

        retMap.put("payment_product_list", null);
        retMap.put("payment_product_category_list", null);

        retMap.put("eci", null);
        retMap.put("authentication_indicator", null);

        //TODO check what multiUse returns
        retMap.put("multi_use", String.valueOf(paymentPageRequest.getMultiUse()));
        retMap.put("display_selector", String.valueOf(paymentPageRequest.getDisplaySelector()));

        retMap.put("template", paymentPageRequest.getTemplateName());

        URL cssUrl = paymentPageRequest.getCss();
        if (cssUrl != null) {
            retMap.put("css",(cssUrl.toString()));
        }

        return retMap;
    }
}
