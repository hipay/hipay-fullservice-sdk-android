package com.hipay.fullservice.core.models;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.OrderMapper;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by nfillion on 25/01/16.
 */
public class Order extends AbstractModel {

    protected String orderId;
    protected Date dateCreated;
    protected Integer attempts;
    protected Float amount;
    protected Float shipping;
    protected Float tax;
    protected Integer decimals;
    protected String currency;
    protected String customerId;
    protected String language;

    protected Gender gender;
    protected PersonalInformation shippingAddress;

    public Order() {
    }

    public static Order fromJSONObject(JSONObject object) {

        OrderMapper mapper = new OrderMapper(object);
        return mapper.mappedObject();
    }

    public static Order fromBundle(Bundle bundle) {

        OrderMapper mapper = new OrderMapper(bundle);
        return mapper.mappedObjectFromBundle();
    }

    public Bundle toBundle() {

        Order.OrderSerializationMapper mapper = new Order.OrderSerializationMapper(this);
        return mapper.getSerializedBundle();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getAttemps() {
        return attempts;
    }

    public void setAttempts(Integer attemps) {
        this.attempts = attemps;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getShipping() {
        return shipping;
    }

    public void setShipping(Float shipping) {
        this.shipping = shipping;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public PersonalInformation getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(PersonalInformation shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public enum Gender {

        GenderUndefined (' '),
        GenderUnknown ('U'),
        GenderMale ('M'),
        GenderFemale ('F');

        protected final char gender;
        Gender(char gender) {
            this.gender = gender;
        }

        public char getCharValue() {

            return this.gender;
        }

        public static Gender fromStringValue(String value) {

            if (value == null) return null;

            char c = value.charAt(0);

            if (c == GenderUnknown.getCharValue()) {
                return GenderUnknown;
            }

            if (c == GenderMale.getCharValue()) {
                return GenderMale;
            }

            if (c == GenderFemale.getCharValue()) {
                return GenderFemale;
            }

            return null;
        }
    }

    protected static class OrderSerializationMapper extends AbstractSerializationMapper {

        protected OrderSerializationMapper(Order order) {
            super(order);
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
