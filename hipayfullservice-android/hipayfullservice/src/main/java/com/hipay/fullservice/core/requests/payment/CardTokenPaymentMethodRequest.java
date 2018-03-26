package com.hipay.fullservice.core.requests.payment;

import com.hipay.fullservice.core.serialization.SerializationFactory;
import com.hipay.fullservice.core.serialization.interfaces.ISerialization;
import com.hipay.fullservice.core.utils.enums.AuthenticationIndicator;
import com.hipay.fullservice.core.utils.enums.ECI;

import java.util.Map;

/**
 * Created by nfillion on 03/02/16.
 */
public class CardTokenPaymentMethodRequest extends AbstractPaymentMethodRequest {

    protected String cardToken;
    protected ECI eci;
    protected AuthenticationIndicator authenticationIndicator;

    public CardTokenPaymentMethodRequest() {

        this.setEci(ECI.UNDEFINED);
    }

    public CardTokenPaymentMethodRequest(String cardToken, ECI eci, AuthenticationIndicator authenticationIndicator) {
        this.cardToken = cardToken;
        this.eci = eci;
        this.authenticationIndicator = authenticationIndicator;
    }

    public Map<String, String> getSerializedObject() {
        ISerialization mapper = SerializationFactory.newInstance(this);
        return mapper.getSerializedRequest();
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public ECI getEci() {
        return eci;
    }

    public void setEci(ECI eci) {
        this.eci = eci;
    }

    public AuthenticationIndicator getAuthenticationIndicator() {
        return authenticationIndicator;
    }

    public void setAuthenticationIndicator(AuthenticationIndicator authenticationIndicator) {
        this.authenticationIndicator = authenticationIndicator;
    }

}
