package com.hipay.fullservice.core.models;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.PaymentCardTokenMapper;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;

import org.json.JSONObject;

/**
 * Created by nfillion on 08/03/16.
 */
public class PaymentCardToken extends PaymentMethod {

    protected String token;
    protected String brand;
    protected String requestID;
    protected String pan;
    protected String cardHolder;
    protected Integer cardExpiryMonth;
    protected Integer cardExpiryYear;
    protected String issuer;
    protected String country;
    protected String domesticNetwork;

    public static PaymentCardToken fromJSONObject(JSONObject object) {

        PaymentCardTokenMapper mapper = new PaymentCardTokenMapper(object);
        return mapper.mappedObject();
    }

    public static PaymentCardToken fromBundle(Bundle bundle) {

        PaymentCardTokenMapper mapper = new PaymentCardTokenMapper(bundle);
        return mapper.mappedObjectFromBundle();
    }

    public Bundle toBundle() {

        PaymentCardToken.PaymentCardTokenSerializationMapper mapper = new PaymentCardToken.PaymentCardTokenSerializationMapper(this);
        return mapper.getSerializedBundle();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Integer getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    public void setCardExpiryMonth(Integer cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    public Integer getCardExpiryYear() {
        return cardExpiryYear;
    }

    public void setCardExpiryYear(Integer cardExpiryYear) {
        this.cardExpiryYear = cardExpiryYear;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDomesticNetwork() {
        return domesticNetwork;
    }

    public void setDomesticNetwork(String domesticNetwork) {
        this.domesticNetwork = domesticNetwork;
    }

    @Override
    public boolean equals(Object object) {

        if (object != null && object instanceof PaymentCardToken && this.getToken() != null)
        {
            return ((PaymentCardToken) object).getToken().equals(this.getToken());
        }

        return false;
    }

    protected static class PaymentCardTokenSerializationMapper extends AbstractSerializationMapper {

        protected PaymentCardTokenSerializationMapper(PaymentCardToken paymentProduct) {
            super(paymentProduct);
        }

        @Override
        protected String getQueryString() {

            return super.getQueryString();
        }

        @Override
        protected Bundle getSerializedBundle() {

            return super.getSerializedBundle();
        }
    }
}
