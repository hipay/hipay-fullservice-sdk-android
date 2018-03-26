package com.hipay.fullservice.core.serialization.interfaces.payment;

import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.core.serialization.interfaces.AbstractSerialization;
import com.hipay.fullservice.core.utils.Utils;
import com.hipay.fullservice.core.utils.enums.AuthenticationIndicator;
import com.hipay.fullservice.core.utils.enums.ECI;

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

        ECI eci = cardTokenPaymentMethodRequest.getEci();
        if (eci != null && eci != ECI.UNDEFINED) {
            retMap.put("eci", String.valueOf(eci.getValue()));
        }

        AuthenticationIndicator authenticationIndicator = cardTokenPaymentMethodRequest.getAuthenticationIndicator();
        if (authenticationIndicator != null) {
            retMap.put("authentication_indicator", String.valueOf(authenticationIndicator.getValue()));
        }

        return retMap;
    }

    @Override
    public String getQueryString() {
        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}
