package com.hipay.fullservice.core.requests.order;

import com.hipay.fullservice.core.requests.payment.AbstractPaymentMethodRequest;
import com.hipay.fullservice.core.serialization.SerializationFactory;
import com.hipay.fullservice.core.serialization.interfaces.ISerialization;

/**
 * Created by nfillion on 03/02/16.
 */
public class OrderRequest extends OrderRelatedRequest {

    public OrderRequest(OrderRelatedRequest orderRelatedRequest) {
        super(orderRelatedRequest);
    }

    public OrderRequest() {
        super();
    }

    protected String paymentProductCode;
    protected AbstractPaymentMethodRequest paymentMethod;

    public String getStringParameters() {
        ISerialization mapper = SerializationFactory.newInstance(this);
        return mapper.getQueryString();
    }

    public String getPaymentProductCode() {
        return paymentProductCode;
    }

    public void setPaymentProductCode(String paymentProductCode) {
        this.paymentProductCode = paymentProductCode;
    }

    public AbstractPaymentMethodRequest getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(AbstractPaymentMethodRequest paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
