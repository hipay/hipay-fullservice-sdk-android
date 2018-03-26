package com.hipay.fullservice.core.serialization.interfaces.order;

import com.hipay.fullservice.core.requests.AbstractRequest;
import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;

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

        AbstractRequest paymentMethodRequest = orderRequest.getPaymentMethod();

        //we don't have qiwi wallet token or ideal for now
        if (paymentMethodRequest instanceof CardTokenPaymentMethodRequest) {

            CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest = (CardTokenPaymentMethodRequest)paymentMethodRequest;
            Map<String, String> cardTokenSerializedObject = cardTokenPaymentMethodRequest.getSerializedObject();

            relatedRequestMap.putAll(cardTokenSerializedObject);
        }

        return relatedRequestMap;
    }
}
