package com.hipay.fullservice.core.serialization.interfaces.payment;

import android.os.Bundle;

import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.core.serialization.interfaces.AbstractSerialization;
import com.hipay.fullservice.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 11/03/16.
 */
public class CardTokenPaymentMethodRequestSerialization extends AbstractSerialization {

    public CardTokenPaymentMethodRequestSerialization(CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest) {
        super(cardTokenPaymentMethodRequest);
    }

    public Map<String, String> getSerializedRequest() {

        Map<String, String> retMap = new HashMap<>();
        CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest = (CardTokenPaymentMethodRequest)this.getModel();

        retMap.put("cardtoken", cardTokenPaymentMethodRequest.getCardToken());

        Transaction.ECI eci = cardTokenPaymentMethodRequest.getEci();
        if (eci != null && eci != Transaction.ECI.Undefined) {
            retMap.put("eci", String.valueOf(eci.getIntegerValue()));
        }

        CardTokenPaymentMethodRequest.AuthenticationIndicator authenticationIndicator = cardTokenPaymentMethodRequest.getAuthenticationIndicator();
        if (authenticationIndicator != null) {
            retMap.put("authentication_indicator", String.valueOf(authenticationIndicator.getIntegerValue()));
        }

        return retMap;
    }

    @Override
    public Bundle getSerializedBundle() {
        return null;
    }

    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}
