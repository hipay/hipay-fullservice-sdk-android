package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.models.PaymentCardToken;

/**
 * Created by HiPay on 08/09/16.
 */

public class PaymentCardTokenMapper extends AbstractMapper {
    public PaymentCardTokenMapper(Object object) {
        super(object);
    }

    @Override
    protected boolean isValid() {

        if (this.getStringForKey("token") != null) return true;
        return false;
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
        object.setDomesticNetwork(this.getStringForKey("domestic_network"));
        object.setDateAdded(this.getDateForKey("dateAdded"));

        return object;
    }

    @Override
    public PaymentCardToken mappedObjectFromBundle() {

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
        object.setDomesticNetwork(this.getStringForKey("domestic_network"));
        object.setDateAdded(this.getDateForKey("dateAdded"));

        return object;
    }

    @Override
    protected Object mappedObjectFromUri() {
        return null;
    }
}

