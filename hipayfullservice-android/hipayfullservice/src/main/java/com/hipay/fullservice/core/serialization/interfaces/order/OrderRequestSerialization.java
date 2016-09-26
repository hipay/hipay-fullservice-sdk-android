package com.hipay.fullservice.core.serialization.interfaces.order;

import android.os.Bundle;

import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.payment.AbstractPaymentMethodRequest;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.core.utils.Utils;

import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public class OrderRequestSerialization extends OrderRelatedRequestSerialization {

    public OrderRequestSerialization(OrderRequest orderRequest) {
        super(orderRequest);
    }

    @Override
    public Map<String, String> getSerializedRequest() {

        Map<String, String> relatedRequestMap = super.getSerializedRequest();

        OrderRequest orderRequest = (OrderRequest)this.getModel();

        relatedRequestMap.put("payment_product", orderRequest.getPaymentProductCode());

        //TODO add payment method
        AbstractPaymentMethodRequest paymentMethodRequest = orderRequest.getPaymentMethod();

        //TODO the handling better
        if (paymentMethodRequest instanceof CardTokenPaymentMethodRequest) {

            CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest = (CardTokenPaymentMethodRequest)paymentMethodRequest;
            Map<String, String> cardTokenSerializedObject = cardTokenPaymentMethodRequest.getSerializedObject();

            relatedRequestMap.putAll(cardTokenSerializedObject);
        }

        return relatedRequestMap;
    }

    @Override
    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }

    @Override
    public Bundle getSerializedBundle() {

        //TODO check about this bundle
        return super.getSerializedBundle();
    }
}
