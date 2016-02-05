package com.hipay.hipayfullservice.core.serialization.interfaces.info;

import com.hipay.hipayfullservice.core.requests.info.PersonalInfoRequest;
import com.hipay.hipayfullservice.core.serialization.interfaces.AbstractRequestSerialization;
import com.hipay.hipayfullservice.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 07/02/16.
 */

public class PersonalInfoRequestSerialization extends AbstractRequestSerialization {

    public PersonalInfoRequestSerialization(PersonalInfoRequest personalInfoRequest) {

        this.setRequest(personalInfoRequest);
    }

    @Override
    public Map<String, String> getSerializedRequest() {

        PersonalInfoRequest personalInfoRequest = (PersonalInfoRequest)this.getRequest();

        Map<String, String> retMap = new HashMap<>();

        retMap.put("firstname", personalInfoRequest.getFirstname());
        retMap.put("lastname", personalInfoRequest.getLastname());
        retMap.put("streetaddress", personalInfoRequest.getStreetAddress());
        retMap.put("streetaddress2", personalInfoRequest.getStreetAddress2());
        retMap.put("recipientinfo", personalInfoRequest.getRecipientInfo());
        retMap.put("city", personalInfoRequest.getCity());
        retMap.put("state", personalInfoRequest.getState());
        retMap.put("zipcode", personalInfoRequest.getZipCode());
        retMap.put("country", personalInfoRequest.getCountry());

        return retMap;
    }

    @Override
    public String getQueryString() {

        return Utils.queryStringFromMap(this.getSerializedRequest());
    }
}
