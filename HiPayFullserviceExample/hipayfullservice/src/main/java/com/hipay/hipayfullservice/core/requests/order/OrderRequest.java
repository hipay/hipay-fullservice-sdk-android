package com.hipay.hipayfullservice.core.requests.order;

import com.hipay.hipayfullservice.core.mapper.interfaces.MapBehaviour;
import com.hipay.hipayfullservice.core.models.PersonalInformation;
import com.hipay.hipayfullservice.core.requests.AbstractRequest;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;

import java.util.Map;

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


    public static class OrderRequestSerializationMapper extends AbstractSerializationMapper {

        public OrderRequestSerializationMapper(AbstractRequest request) {
            super(request);
        }

        @Override
        protected Map<String, String> getSerializedObject() {

            return super.getSerializedObject();
        }
    }

}
