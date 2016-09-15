package com.hipay.hipayfullservice.core.mapper;

import com.hipay.hipayfullservice.core.models.Order;

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
        Order.Gender gender = Order.Gender.fromStringValue(genderString);
        if (gender == null) {
            gender = Order.Gender.GenderUndefined;
        }
        object.setGender(gender);

        object.setDateCreated(this.getDateForKey("dateCreated"));

        //TODO shipping address

        return object;

    }

    @Override
    public Order mappedObjectFromBundle() {

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
        Order.Gender gender = Order.Gender.fromStringValue(genderString);
        if (gender == null) {
            gender = Order.Gender.GenderUndefined;
        }
        object.setGender(gender);
        object.setDateCreated(this.getDateForKey("dateCreated"));

        //TODO need to map personal shipping

        return object;
    }
}

