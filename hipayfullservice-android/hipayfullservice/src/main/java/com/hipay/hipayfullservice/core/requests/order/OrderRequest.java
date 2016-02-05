package com.hipay.hipayfullservice.core.requests.order;

import com.hipay.hipayfullservice.core.requests.AbstractRequest;
import com.hipay.hipayfullservice.core.requests.payment.AbstractPaymentMethodRequest;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;

/**
 * Created by nfillion on 03/02/16.
 */
public class OrderRequest extends OrderRelatedRequest {

    public OrderRequest() {
    }

    protected String paymentProductCode;
    protected AbstractPaymentMethodRequest paymentMethod;


    public String getPaymentProductCode() {
        return paymentProductCode;
    }

    public void setPaymentProductCode(String paymentProductCode) {
        this.paymentProductCode = paymentProductCode;
    }


    public static class OrderRequestSerializationMapper extends AbstractSerializationMapper {

        public OrderRequestSerializationMapper(AbstractRequest request) {
            super(request);
        }

    }

}
