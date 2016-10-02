package com.hipay.fullservice.core.mapper;

import android.os.Bundle;

import com.hipay.fullservice.core.models.Order;
import com.hipay.fullservice.core.models.PersonalInformation;

import org.json.JSONObject;

/**
 * Created by nfillion on 08/09/16.
 */

public class OrderMapper extends PersonalInformationMapper {
    public OrderMapper(Object rawData) {
        super(rawData);
    }

    @Override
    protected boolean isValid() {

        if (this.getStringForKey("id") != null) {
            return true;
        }

        return false;
    }

    @Override
    public Order mappedObject() {

        PersonalInformation personalInformation = super.mappedObject();
        Order object = this.orderFromPersonalInformation(personalInformation);

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
        Order.Gender gender = Order.Gender.fromStringValue(genderString);
        if (gender == null) {
            gender = Order.Gender.GenderUndefined;
        }
        object.setGender(gender);

        object.setDateCreated(this.getDateForKey("dateCreated"));

        JSONObject shippingObject = this.getJSONObjectForKey("shippingAddress");
        PersonalInformation personalInfo = null;
        if (shippingObject != null) {
            personalInfo= PersonalInformation.fromJSONObject(shippingObject);
        }
        object.setShippingAddress(personalInfo);

        return object;

    }

    @Override
    public Order mappedObjectFromBundle() {

        PersonalInformation personalInformation = super.mappedObjectFromBundle();
        Order object = this.orderFromPersonalInformation(personalInformation);

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
        Order.Gender gender = Order.Gender.fromStringValue(genderString);
        if (gender == null) {
            gender = Order.Gender.GenderUndefined;
        }
        object.setGender(gender);
        object.setDateCreated(this.getDateForKey("dateCreated"));

        Bundle shippingBundle = this.getBundleForKey("shippingAddress");
        PersonalInformation personalInfo = null;
        if (shippingBundle != null) {
            personalInfo = PersonalInformation.fromBundle(shippingBundle);
        }
        object.setShippingAddress(personalInfo);

        return object;
    }

    private Order orderFromPersonalInformation(PersonalInformation personalInformation) {

        Order order = new Order();

        order.setFirstname(personalInformation.getFirstname());
        order.setLastname(personalInformation.getLastname());
        order.setStreetAddress(personalInformation.getStreetAddress());
        order.setLocality(personalInformation.getLocality());
        order.setPostalCode(personalInformation.getPostalCode());
        order.setCountry(personalInformation.getCountry());
        order.setMsisdn(personalInformation.getMsisdn());
        order.setPhone(personalInformation.getPhone());
        order.setPhoneOperator(personalInformation.getPhoneOperator());
        order.setEmail(personalInformation.getEmail());

        return order;
    }
}

