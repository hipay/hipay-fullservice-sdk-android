package com.hipay.hipayfullservice.core.mapper;

import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.models.PaymentCardToken;

import org.json.JSONObject;

/**
 * Created by nfillion on 08/09/16.
 */

public class PaymentCardTokenMapper extends AbstractMapper {
    public PaymentCardTokenMapper(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    protected boolean isValid() {

        if (this.getBehaviour() instanceof MapMapper) {

            if (this.getStringForKey("token") != null) return true;
        }

        return false;
    }

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

    @Override
    public Object mappedObjectFromBundle() {
        return null;
    }
}

