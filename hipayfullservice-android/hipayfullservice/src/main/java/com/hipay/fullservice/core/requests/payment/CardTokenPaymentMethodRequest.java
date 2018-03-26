package com.hipay.fullservice.core.requests.payment;

import com.hipay.fullservice.core.requests.AbstractRequest;
import com.hipay.fullservice.core.utils.enums.AuthenticationIndicator;
import com.hipay.fullservice.core.utils.enums.ECI;

/**
 * Created by nfillion on 03/02/16.
 */
public class CardTokenPaymentMethodRequest extends AbstractRequest {

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
