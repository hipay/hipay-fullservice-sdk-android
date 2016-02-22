package com.hipay.hipayfullservice.core.models;

import com.hipay.hipayfullservice.core.mapper.interfaces.MapBehaviour;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by nfillion on 25/01/16.
 */
public class Order {

    protected String orderId;
    protected Date dateCreated;
    protected Integer attempts;
    protected Number amount;
    protected Number shipping;
    protected Number tax;
    protected Number decimals;
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

    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }

    public Number getShipping() {
        return shipping;
    }

    public void setShipping(Number shipping) {
        this.shipping = shipping;
    }

    public Number getTax() {
        return tax;
    }

    public void setTax(Number tax) {
        this.tax = tax;
    }

    public Number getDecimals() {
        return decimals;
    }

    public void setDecimals(Number decimals) {
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


    public static class OrderMapper extends PersonalInformation.PersonalInformationMapper {
        public OrderMapper(JSONObject object) {
            super(object);
        }

        @Override
        protected boolean isValid() {

            if (this.getBehaviour() instanceof MapBehaviour) {

                if (super.isValid()) {
                    if (this.getStringForKey("id") != null) {
                        return true;
                    }
                }
            }

            return false;
        }

        protected Order mappedObject() {

            //TODO build operation object from transactionRelatedItem

            //HPFOrder *object = [self mappedObjectWithPersonalInformation:[[HPFOrder alloc] init]];
            Order object = new Order();

            object.setCurrency(this.getStringForKey("currency"));
            object.setCustomerId(this.getStringForKey("customerId"));
            object.setLanguage(this.getStringForKey("language"));
            object.setOrderId(this.getStringForKey("id"));
            object.setAttempts(this.getIntegerForKey("attempts"));
            object.setAmount(this.getNumberForKey("amount"));
            object.setShipping(this.getNumberForKey("shipping"));
            object.setTax(this.getNumberForKey("tax"));
            object.setDecimals(this.getNumberForKey("decimals"));

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
