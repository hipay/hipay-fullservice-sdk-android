package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.requests.info.PersonalInfoRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 26/03/2018.
 */
public class PersonalInfoRequestSerialization extends AbstractSerialization {

    public PersonalInfoRequestSerialization(PersonalInfoRequest personalInfoRequest) {

        super(personalInfoRequest);
    }

    @Override
    public Map<String, String> getSerializedRequest() {

        PersonalInfoRequest personalInfoRequest = (PersonalInfoRequest) this.getModel();

        Map<String, String> personalInfoRequestMap = new HashMap<>();

        personalInfoRequestMap.put("firstname", personalInfoRequest.getFirstname());
        personalInfoRequestMap.put("lastname", personalInfoRequest.getLastname());
        personalInfoRequestMap.put("streetaddress", personalInfoRequest.getStreetAddress());
        personalInfoRequestMap.put("streetaddress2", personalInfoRequest.getStreetAddress2());
        personalInfoRequestMap.put("recipientinfo", personalInfoRequest.getRecipientInfo());
        personalInfoRequestMap.put("city", personalInfoRequest.getCity());
        personalInfoRequestMap.put("state", personalInfoRequest.getState());
        personalInfoRequestMap.put("zipcode", personalInfoRequest.getZipCode());
        personalInfoRequestMap.put("country", personalInfoRequest.getCountry());

        return personalInfoRequestMap;
    }

    @Override
    public Bundle getSerializedBundle() {
        PersonalInfoRequest personalInfoRequest = (PersonalInfoRequest) this.getModel();

        this.bundle.putString("firstname", personalInfoRequest.getFirstname());
        this.bundle.putString("lastname", personalInfoRequest.getLastname());
        this.bundle.putString("streetaddress", personalInfoRequest.getStreetAddress());
        this.bundle.putString("streetaddress2", personalInfoRequest.getStreetAddress2());
        this.bundle.putString("recipientinfo", personalInfoRequest.getRecipientInfo());
        this.bundle.putString("city", personalInfoRequest.getCity());
        this.bundle.putString("state", personalInfoRequest.getState());
        this.bundle.putString("zipcode", personalInfoRequest.getZipCode());
        this.bundle.putString("country", personalInfoRequest.getCountry());

        return this.getBundle();
    }
}
