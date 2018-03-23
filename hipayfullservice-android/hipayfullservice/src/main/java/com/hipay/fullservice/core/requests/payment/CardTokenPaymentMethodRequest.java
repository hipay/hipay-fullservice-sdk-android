package com.hipay.fullservice.core.requests.payment;

import com.hipay.fullservice.core.requests.AbstractRequest;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;
import com.hipay.fullservice.core.utils.enums.ECI;
import com.hipay.fullservice.core.utils.enums.AuthenticationIndicator;

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

        CardTokenPaymentMethodRequest.CardTokenPaymentMethodRequestSerializationMapper mapper = new CardTokenPaymentMethodRequest.CardTokenPaymentMethodRequestSerializationMapper(this);
        return mapper.getSerializedObject();
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

    public static class CardTokenPaymentMethodRequestSerializationMapper extends AbstractSerializationMapper {

        public CardTokenPaymentMethodRequestSerializationMapper(AbstractRequest request) {
            super(request);
        }

        @Override
        public Map<String, String> getSerializedObject() {
            return super.getSerializedObject();
        }
    }
}
