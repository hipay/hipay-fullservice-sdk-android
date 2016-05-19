package com.hipay.hipayfullservice.core.models;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;
import com.hipay.hipayfullservice.core.serialization.interfaces.AbstractSerialization;

import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

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

    public static class OrderSerialization extends AbstractSerialization {

        //TODO time to put a rawData instead of model/request in initializer
        public OrderSerialization(Order order) {
            super(order);
        }

        @Override
        public Map<String, String> getSerializedRequest() {
            return null;
        }

        @Override
        public Bundle getSerializedBundle() {
            super.getSerializedBundle();

            Order order = (Order)this.getModel();

            this.putStringForKey("currency", order.getCurrency());
            this.putStringForKey("customerID", order.getCustomerId());
            this.putStringForKey("language", order.getLanguage());
            this.putStringForKey("id", order.getOrderId());
            this.putIntForKey("attempts", order.getAttemps());
            this.putFloatForKey("amount", order.getAmount());
            this.putFloatForKey("shipping", order.getShipping());
            this.putFloatForKey("tax", order.getTax());
            this.putIntForKey("decimals", order.getDecimals());

            Gender gender = order.getGender();
            if (gender != null) {
                this.putStringForKey("gender", Character.toString(gender.getCharValue()));
            }

            this.putDateForKey("dateCreated", order.getDateCreated());

            return this.getBundle();
        }

        @Override
        public String getQueryString() {
            return null;
        }
    }

    public static class OrderMapper extends PersonalInformation.PersonalInformationMapper {
        public OrderMapper(Object rawData) {
            super(rawData);
        }

        @Override
        protected boolean isValid() {

            if (this.getBehaviour() instanceof MapMapper) {

                if (super.isValid()) {
                    if (this.getStringForKey("id") != null) {
                        return true;
                    }
                }
            }

            return false;
        }

        @Override
        protected Order mappedObject() {

            Order object = new Order();

            object.setCurrency(this.getStringForKey("currency"));
            object.setCustomerId(this.getStringForKey("customerId"));
            object.setLanguage(this.getStringForKey("language"));
            object.setOrderId(this.getStringForKey("id"));
            object.setAttempts(this.getIntegerForKey("attempts"));
            object.setAmount(this.getFloatForKey("amount"));
            object.setShipping(this.getFloatForKey("shipping"));
            object.setTax(this.getFloatForKey("tax"));
            object.setDecimals(this.getIntegerForKey("decimals"));

            String genderString = this.getEnumCharForKey("gender");
            Gender gender = Gender.fromStringValue(genderString);
            if (gender == null) {
                gender = Gender.GenderUndefined;
            }
            object.setGender(gender);

            object.setDateCreated(this.getDateForKey("dateCreated"));

            return object;

        }

        @Override
        protected Order mappedObjectFromBundle() {

            Order object = new Order();

            object.setCurrency(this.getStringForKey("currency"));
            object.setCustomerId(this.getStringForKey("customerId"));
            object.setLanguage(this.getStringForKey("language"));
            object.setOrderId(this.getStringForKey("id"));
            object.setAttempts(this.getIntegerForKey("attempts"));
            object.setAmount(this.getFloatForKey("amount"));
            object.setShipping(this.getFloatForKey("shipping"));
            object.setTax(this.getFloatForKey("tax"));
            object.setDecimals(this.getIntegerForKey("decimals"));

            String genderString = this.getEnumCharForKey("gender");
            Gender gender = Gender.fromStringValue(genderString);
            if (gender == null) {
                gender = Gender.GenderUndefined;
            }
            object.setGender(gender);
            object.setDateCreated(this.getDateForKey("dateCreated"));

            return object;
        }
    }
}
