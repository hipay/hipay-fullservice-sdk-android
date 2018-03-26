package com.hipay.fullservice.core.requests.info;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.CustomerInfoRequestMapper;
import com.hipay.fullservice.core.utils.enums.Gender;

import org.json.JSONObject;

/**
 * Created by nfillion on 03/02/16.
 */
public class CustomerInfoRequest extends PersonalInfoRequest {

    public CustomerInfoRequest() {
    }

    public static CustomerInfoRequest fromBundle(Bundle bundle) {

        CustomerInfoRequestMapper mapper = new CustomerInfoRequestMapper(bundle);
        return mapper.mappedObject();
    }

    public static CustomerInfoRequest fromJSONObject(JSONObject jsonObject) {

        CustomerInfoRequestMapper mapper = new CustomerInfoRequestMapper(jsonObject);
        return mapper.mappedObject();
    }


    protected String email;
    protected String phone;
    protected Integer birthDateDay;
    protected Integer birthDateMonth;
    protected Integer birthDateYear;
    protected Gender gender;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getBirthDateDay() {
        return birthDateDay;
    }

    public void setBirthDateDay(Integer birthDateDay) {
        this.birthDateDay = birthDateDay;
    }

    public Integer getBirthDateMonth() {
        return birthDateMonth;
    }

    public void setBirthDateMonth(Integer birthDateMonth) {
        this.birthDateMonth = birthDateMonth;
    }

    public Integer getBirthDateYear() {
        return birthDateYear;
    }

    public void setBirthDateYear(Integer birthDateYear) {
        this.birthDateYear = birthDateYear;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
