package com.hipay.fullservice.core.requests.payment;

import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.AbstractRequest;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;

import java.util.Map;

/**
 * Created by nfillion on 03/02/16.
 */
public class CardTokenPaymentMethodRequest extends AbstractPaymentMethodRequest {

    protected String cardToken;
    protected Transaction.ECI eci;
    protected AuthenticationIndicator authenticationIndicator;

    public CardTokenPaymentMethodRequest() {

        this.setEci(Transaction.ECI.Undefined);
    }

    public CardTokenPaymentMethodRequest(String cardToken, Transaction.ECI eci, AuthenticationIndicator authenticationIndicator) {
        this.cardToken = cardToken;
        this.eci = eci;
        this.authenticationIndicator = authenticationIndicator;
    }

    public Map<String, String> getSerializedObject() {

        CardTokenPaymentMethodRequest.CardTokenPaymentMethodRequestSerializationMapper mapper = new CardTokenPaymentMethodRequest.CardTokenPaymentMethodRequestSerializationMapper(this);
        return mapper.getSerializedObject();
    }

    public enum AuthenticationIndicator {

        //Undefined (-1),
        Bypass(0),
        IfAvailable(1),
        Mandatory(2);

        protected final Integer indicator;
        AuthenticationIndicator(Integer indicator) {

            this.indicator = indicator;
        }

        public static AuthenticationIndicator fromIntegerValue(Integer value) {

            if (value == null) return null;

            if (value.equals(Bypass.getIntegerValue())) {
                return Bypass;
            }

            if (value.equals(IfAvailable.getIntegerValue())) {

                return IfAvailable;
            }

            if (value.equals(Mandatory.getIntegerValue())) {
                return Mandatory;
            }

            return null;
        }

        public Integer getIntegerValue() {
            return this.indicator;
        }
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public Transaction.ECI getEci() {
        return eci;
    }

    public void setEci(Transaction.ECI eci) {
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
