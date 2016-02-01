package com.hipay.hipayfullservice.core.requests.order;

/**
 * Created by nfillion on 03/02/16.
 */
public class OrderRequest extends OrderRelatedRequest {

    public OrderRequest() {
    }

    protected String paymentProductCode;
    //TODO protected AbstractPaymentRequest


    public String getPaymentProductCode() {
        return paymentProductCode;
    }

    public void setPaymentProductCode(String paymentProductCode) {
        this.paymentProductCode = paymentProductCode;
    }
}
