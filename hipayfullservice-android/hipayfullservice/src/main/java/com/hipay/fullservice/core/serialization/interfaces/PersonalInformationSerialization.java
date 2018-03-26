package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.models.PersonalInformation;

/**
 * Created by nfillion on 08/09/16.
 */

public class PersonalInformationSerialization extends AbstractSerialization {

    public PersonalInformationSerialization(PersonalInformation personalInformation) {
        super(personalInformation);
    }

    @Override
    public Bundle getSerializedBundle() {
        PersonalInformation personalInformation = (PersonalInformation) this.getModel();

        this.bundle.putString("firstname", personalInformation.getFirstname());
        this.bundle.putString("lastname", personalInformation.getLastname());
        this.bundle.putString("streetAddress", personalInformation.getStreetAddress());
        this.bundle.putString("streetLocality", personalInformation.getLocality());
        this.bundle.putString("postalCode", personalInformation.getPostalCode());
        this.bundle.putString("country", personalInformation.getCountry());
        this.bundle.putString("msisdn", personalInformation.getMsisdn());
        this.bundle.putString("phone", personalInformation.getPhone());
        this.bundle.putString("phoneOperator", personalInformation.getPhoneOperator());
        this.bundle.putString("email", personalInformation.getEmail());

        return this.bundle.getBundle();
    }
}

