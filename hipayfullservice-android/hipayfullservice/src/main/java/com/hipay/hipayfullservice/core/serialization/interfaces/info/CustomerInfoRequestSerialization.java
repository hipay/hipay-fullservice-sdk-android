package com.hipay.hipayfullservice.core.serialization.interfaces.info;

import com.hipay.hipayfullservice.core.models.Order;
import com.hipay.hipayfullservice.core.requests.info.CustomerInfoRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 07/02/16.
 */
public class CustomerInfoRequestSerialization extends PersonalInfoRequestSerialization{
    public CustomerInfoRequestSerialization(CustomerInfoRequest customerInfoRequest) {
        super(customerInfoRequest);
    }

    @Override
    public Map<String, String> getSerializedRequest() {

        CustomerInfoRequest customerInfoRequest = (CustomerInfoRequest)this.getRequest();

        Map<String, String> personalInfoRequestMap = super.getSerializedRequest();

        //Map<String, String> retMap = new HashMap<>();

        personalInfoRequestMap.put("email", customerInfoRequest.getEmail());
        personalInfoRequestMap.put("phone", customerInfoRequest.getPhone());
        personalInfoRequestMap.put("birthdate", customerInfoRequest.getBirthDateMonth());

        Order.Gender gender = customerInfoRequest.getGender();
        if (gender != null && gender != Order.Gender.GenderUndefined) {
            personalInfoRequestMap.put("gender", String.valueOf(gender.getCharValue()));
        }

        return personalInfoRequestMap;
    }

    @Override
    public String getQueryString() {
        return null;
    }
}
