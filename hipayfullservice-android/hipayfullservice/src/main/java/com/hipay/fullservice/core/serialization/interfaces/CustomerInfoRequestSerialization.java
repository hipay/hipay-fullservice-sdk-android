package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.fullservice.core.utils.enums.Gender;

import java.util.Map;

/**
 * Created by nfillion on 23/03/2018.
 */
public class CustomerInfoRequestSerialization extends PersonalInfoRequestSerialization {

    public CustomerInfoRequestSerialization(CustomerInfoRequest customerInfoRequest) {
        super(customerInfoRequest);
    }

    private String getBirthDate(Integer birthDateDay, Integer birthDateMonth, Integer birthDateYear) {

        if (
                birthDateDay != null &&
                        birthDateDay.intValue() >= 1 &&
                        birthDateDay.intValue() <= 31 &&

                        birthDateMonth != null &&
                        birthDateMonth.intValue() >= 1 &&
                        birthDateMonth.intValue() <= 12 &&

                        birthDateYear != null &&
                        birthDateYear.intValue() >= 1 &&
                        birthDateYear.intValue() <= 3000
                ) {

            StringBuilder birthDateBuilder = new StringBuilder(birthDateYear.toString()).append(birthDateMonth.toString()).append(birthDateDay.toString());
            return birthDateBuilder.toString();
        }

        return null;
    }

    @Override
    public Map<String, String> getSerializedRequest() {

        CustomerInfoRequest customerInfoRequest = (CustomerInfoRequest) this.getModel();

        Map<String, String> personalInfoRequestMap = super.getSerializedRequest();

        personalInfoRequestMap.put("email", customerInfoRequest.getEmail());
        personalInfoRequestMap.put("phone", customerInfoRequest.getPhone());

        String birthDate = this.getBirthDate(customerInfoRequest.getBirthDateDay(), customerInfoRequest.getBirthDateMonth(), customerInfoRequest.getBirthDateYear());
        personalInfoRequestMap.put("birthdate", birthDate);

        Gender gender = customerInfoRequest.getGender();
        if (gender != null && gender != Gender.UNDEFINED) {
            personalInfoRequestMap.put("gender", String.valueOf(gender.getValue()));
        }

        return personalInfoRequestMap;
    }

    @Override
    public Bundle getSerializedBundle() {

        //super class put data into the bundle first
        super.getSerializedBundle();

        CustomerInfoRequest customerInfoRequest = (CustomerInfoRequest) this.getModel();

        this.bundle.putString("email", customerInfoRequest.getEmail());
        this.bundle.putString("phone", customerInfoRequest.getPhone());

        this.bundle.putInt("birthDateDay", customerInfoRequest.getBirthDateDay());
        this.bundle.putInt("birthDateMonth", customerInfoRequest.getBirthDateMonth());
        this.bundle.putInt("birthDateYear", customerInfoRequest.getBirthDateYear());

        Gender gender = customerInfoRequest.getGender();
        if (gender != null) {
            this.bundle.putString("gender", Character.toString(gender.getValue()));
        }

        return this.getBundle();
    }
}
