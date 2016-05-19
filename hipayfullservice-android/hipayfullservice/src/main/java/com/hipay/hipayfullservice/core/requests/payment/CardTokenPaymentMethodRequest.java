package com.hipay.hipayfullservice.core.requests.payment;

import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.AbstractRequest;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;

import java.util.Map;

/**
 * Created by nfillion on 03/02/16.
 */
public class CardTokenPaymentMethodRequest extends AbstractPaymentMethodRequest {

    protected String cardToken;
    protected Transaction.ECI eci;
    protected AuthenticationIndicator authenticationIndicator;

    public CardTokenPaymentMethodRequest() {

        this.setEci(Transaction.ECI.ECIUndefined);
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

        //AuthenticationIndicatorUndefined (-1),
        AuthenticationIndicatorBypass (0),
        AuthenticationIndicatorIfAvailable (1),
        AuthenticationIndicatorMandatory (2);

        protected final Integer indicator;
        AuthenticationIndicator(Integer indicator) {

            this.indicator = indicator;
        }

        public static AuthenticationIndicator fromIntegerValue(Integer value) {

            if (value == null) return null;

            if (value == AuthenticationIndicatorBypass.getIntegerValue()) {
                return AuthenticationIndicatorBypass;
            }

            if (value == AuthenticationIndicatorIfAvailable.getIntegerValue()) {

                return AuthenticationIndicatorIfAvailable;
            }

            if (value == AuthenticationIndicatorMandatory.getIntegerValue()) {
                return AuthenticationIndicatorMandatory;
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
