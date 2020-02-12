package com.hipay.fullservice.core.monitoring;

import android.util.Log;

import com.hipay.fullservice.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class CheckoutData {

    public static CheckoutData checkoutData;
    protected String identifier;
    protected String status;
    protected String paymentMethod;
    protected String cardCountry;
    protected Float amount;
    protected String currency;
    protected String orderID;
    protected String transactionID;
    protected Event event;
    protected String domain;
    protected Monitoring monitoring;
    protected Components components;

    public CheckoutData() {
        this.setDomain(BuildConfig.APPLICATION_ID);

        String randomID = randomIdentifier();
        if (randomID != null && this.getDomain() != null) {
            String hashKey = getSHA256Hash(randomID + ':' + this.getDomain());
            if (hashKey != null) {
                this.setIdentifier(hashKey);
            }
        }
        setComponents(new Components());
    }

    public String toJSON() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", this.getIdentifier());
            jsonObject.put("status", this.getStatus());
            jsonObject.put("payment_method", this.getPaymentMethod());
            jsonObject.put("card_country", this.getCardCountry());
            jsonObject.put("amount", this.getAmount());
            jsonObject.put("currency", this.getCurrency());
            jsonObject.put("order_id", this.getOrderID());
            jsonObject.put("event", this.getEvent());
            jsonObject.put("domain", this.getDomain());
            jsonObject.put("transaction_id", this.getTransactionID());
            jsonObject.put("event", this.getEvent().name());

            if (this.getMonitoring() != null && this.getMonitoring().toJSONObject().length() > 0) {
                jsonObject.put("monitoring", this.getMonitoring().toJSONObject());
            }
            if (this.getComponents() != null && this.getComponents().toJSONObject().length() > 0) {
                jsonObject.put("components", this.getComponents().toJSONObject());
            }

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String randomIdentifier() {
        return UUID.randomUUID().toString();
    }

    private String getSHA256Hash(String data) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(data.getBytes("UTF-8"));
            // bytes to hex
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
        }

        return result;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Monitoring getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(Monitoring monitoring) {
        this.monitoring = monitoring;
    }

    private Components getComponents() {
        return components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

    private String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardCountry() {
        return cardCountry;
    }

    public void setCardCountry(String cardCountry) {
        this.cardCountry = cardCountry;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public enum Event {
        init,
        tokenize,
        request
    }
}
