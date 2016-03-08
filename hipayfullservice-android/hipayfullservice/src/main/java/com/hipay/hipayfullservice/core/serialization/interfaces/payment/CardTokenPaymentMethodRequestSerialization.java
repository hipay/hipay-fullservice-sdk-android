package com.hipay.hipayfullservice.core.serialization.interfaces.payment;

import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.hipayfullservice.core.serialization.interfaces.AbstractRequestSerialization;
import com.hipay.hipayfullservice.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 11/03/16.
 */
public class CardTokenPaymentMethodRequestSerialization extends AbstractRequestSerialization {

    public CardTokenPaymentMethodRequestSerialization(CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest) {

        this.setRequest(cardTokenPaymentMethodRequest);
    }

    public Map<String, String> getSerializedRequest() {

        Map<String, String> retMap = new HashMap<>();
        CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest = (CardTokenPaymentMethodRequest)this.getRequest();

        retMap.put("cardtoken", cardTokenPaymentMethodRequest.getCardToken());

        Transaction.ECI eci = cardTokenPaymentMethodRequest.getEci();
        if (eci != null && eci != Transaction.ECI.ECIUndefined) {
            retMap.put("eci", String.valueOf(eci.getIntegerValue()));
        }

        CardTokenPaymentMethodRequest.AuthenticationIndicator authenticationIndicator = cardTokenPaymentMethodRequest.getAuthenticationIndicator();
        if (authenticationIndicator != null && authenticationIndicator != CardTokenPaymentMethodRequest.AuthenticationIndicator.AuthenticationIndicatorUndefined) {
            retMap.put("authentication_indicator", String.valueOf(authenticationIndicator.getIntegerValue()));
        }

        return retMap;
    }

    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}
