package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.models.PaymentCardToken;

/**
 * Created by nfillion on 08/09/16.
 */

public class PaymentCardTokenMapper extends AbstractMapper {
    public PaymentCardTokenMapper(Object object) {
        super(object);
    }

    @Override
    public boolean isValid() {
        return this.getStringForKey("token") != null;
    }

    @Override
    public PaymentCardToken mappedObject() {

        PaymentCardToken object = new PaymentCardToken();

        object.setToken(this.getStringForKey("token"));
        object.setRequestID(this.getStringForKey("request_id"));
        object.setPan(this.getStringForKey("pan"));
        object.setBrand(this.getStringForKey("brand"));
        object.setCardHolder(this.getStringForKey("card_holder"));
        object.setCardExpiryMonth(this.getIntegerForKey("card_expiry_month"));
        object.setCardExpiryYear(this.getIntegerForKey("card_expiry_year"));
        object.setIssuer(this.getStringForKey("issuer"));
        object.setCountry(this.getStringForKey("country"));
        object.setDomesticNetwork(this.getStringForKey("domesticNetwork"));

        return object;
    }
}

