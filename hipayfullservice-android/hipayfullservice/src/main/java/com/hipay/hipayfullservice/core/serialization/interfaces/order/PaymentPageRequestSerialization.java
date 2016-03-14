package com.hipay.hipayfullservice.core.serialization.interfaces.order;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.serialization.BundleSerialization;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.utils.Utils;

import java.net.URL;
import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public class PaymentPageRequestSerialization extends OrderRelatedRequestSerialization {

    public PaymentPageRequestSerialization(PaymentPageRequest paymentPageRequest) {

        this.setRequest(paymentPageRequest);
    }

    @Override
    public Map<String, String> getSerializedRequest() {

        Map<String, String> relatedRequestMap = super.getSerializedRequest();

        // get the OrderRelatedRequest serialization
        PaymentPageRequest paymentPageRequest = (PaymentPageRequest)this.getRequest();

        relatedRequestMap.put("payment_product_list", null);
        relatedRequestMap.put("payment_product_category_list", null);

        relatedRequestMap.put("eci", null);
        relatedRequestMap.put("authentication_indicator", null);

        //TODO check what multiUse returns
        relatedRequestMap.put("multi_use", String.valueOf(paymentPageRequest.getMultiUse()));
        relatedRequestMap.put("display_selector", String.valueOf(paymentPageRequest.getDisplaySelector()));

        relatedRequestMap.put("template", paymentPageRequest.getTemplateName());

        URL cssUrl = paymentPageRequest.getCss();
        if (cssUrl != null) {
            relatedRequestMap.put("css", (cssUrl.toString()));
        }

        //TODO after merging, remove every null objects
        while (relatedRequestMap.values().remove(null));

        return relatedRequestMap;
    }

    @Override
    public Bundle getSerializedBundle() {

        //TODO then should be easy to merge serializedRequest and serializedBundle, just change the behaviour dynamically
        this.setBundleBehaviour(new BundleSerialization());

        //TODO must be enough to get the bundle filled
        super.getSerializedBundle();

        PaymentPageRequest paymentPageRequest = (PaymentPageRequest)this.getRequest();

        //TODO handle this
        //relatedRequestMap.put("payment_product_list", null);
        //relatedRequestMap.put("payment_product_category_list", null);

        //relatedRequestMap.put("eci", null);
        //relatedRequestMap.put("authentication_indicator", null);


        //TODO check what multiUse returns

        this.putBoolForKey("multi_use", paymentPageRequest.getMultiUse());
        this.putBoolForKey("display_selector", paymentPageRequest.getDisplaySelector());

        this.putStringForKey("template", paymentPageRequest.getTemplateName());

        URL cssUrl = paymentPageRequest.getCss();
        if (cssUrl != null) {

            this.putStringForKey("css", cssUrl.toString());
        }

        return this.getBundle();
    }

    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}
