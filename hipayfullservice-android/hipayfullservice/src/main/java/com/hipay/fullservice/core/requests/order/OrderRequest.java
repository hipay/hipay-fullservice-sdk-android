package com.hipay.fullservice.core.requests.order;

import com.hipay.fullservice.core.requests.AbstractRequest;
import com.hipay.fullservice.core.requests.payment.AbstractPaymentMethodRequest;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;

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

        OrderRequest.OrderRequestSerializationMapper mapper = new OrderRequest.OrderRequestSerializationMapper(this);
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

    public static class OrderRequestSerializationMapper extends AbstractSerializationMapper {

        public OrderRequestSerializationMapper(AbstractRequest request) {
            super(request);
        }

        @Override
        public String getQueryString() {
            return super.getQueryString();
        }
    }

}
