package com.hipay.hipayfullservice.core.models;

import com.hipay.hipayfullservice.core.mapper.AbstractMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;

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
    protected Number cardExpiryMonth;
    protected Number cardExpiryYear;
    protected String issuer;
    protected String country;
    protected String domesticNetwork;

    public static PaymentCardToken fromJSONObject(JSONObject object) {

        PaymentCardTokenMapper mapper = new PaymentCardTokenMapper(object);
        return mapper.mappedObject();
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

    public Number getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    public void setCardExpiryMonth(Number cardExpiryMonth) {
        this.cardExpiryMonth = cardExpiryMonth;
    }

    public Number getCardExpiryYear() {
        return cardExpiryYear;
    }

    public void setCardExpiryYear(Number cardExpiryYear) {
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

    public static class PaymentCardTokenMapper extends AbstractMapper {
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

        protected PaymentCardToken mappedObject() {

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
        protected Object mappedObjectFromBundle() {
            return null;
        }
    }
}
