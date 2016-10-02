package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.PersonalInformation;

import java.util.Map;

/**
 * Created by nfillion on 08/09/16.
 */

public class PersonalInformationSerialization extends AbstractSerialization {

    public PersonalInformationSerialization(PersonalInformation personalInformation) {
        super(personalInformation);
    }

    @Override
    public Map<String, String> getSerializedRequest() {
        return null;
    }

    @Override
    public Bundle getSerializedBundle() {
        super.getSerializedBundle();

        PersonalInformation personalInformation = (PersonalInformation) this.getModel();

        this.putStringForKey("firstname", personalInformation.getFirstname());
        this.putStringForKey("lastname", personalInformation.getLastname());
        this.putStringForKey("streetAddress", personalInformation.getStreetAddress());
        this.putStringForKey("streetLocality", personalInformation.getLocality());
        this.putStringForKey("postalCode", personalInformation.getPostalCode());
        this.putStringForKey("country", personalInformation.getCountry());
        this.putStringForKey("msisdn", personalInformation.getMsisdn());
        this.putStringForKey("phone", personalInformation.getPhone());
        this.putStringForKey("phoneOperator", personalInformation.getPhoneOperator());
        this.putStringForKey("email", personalInformation.getEmail());

        return this.getBundle();
    }

    @Override
    public String getQueryString() {
        return null;
    }
}

