package com.hipay.fullservice.core.models;

import com.hipay.fullservice.core.mapper.PaymentCardTokenMapper;

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
}
