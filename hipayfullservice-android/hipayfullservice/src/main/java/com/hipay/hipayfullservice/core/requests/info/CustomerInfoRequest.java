package com.hipay.hipayfullservice.core.requests.info;

import com.hipay.hipayfullservice.core.models.Order;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;

import java.util.Map;

/**
 * Created by nfillion on 03/02/16.
 */
public class CustomerInfoRequest extends PersonalInfoRequest {

    public CustomerInfoRequest() {

        this.setGender(Order.Gender.GenderUndefined);
    }

    public String getStringParameters() {

        CustomerInfoRequest.CustomerInfoRequestSerializationMapper mapper = new CustomerInfoRequest.CustomerInfoRequestSerializationMapper(this);
        return mapper.getQueryString();
    }

    public Map<String, String> getSerializedObject() {

        CustomerInfoRequest.CustomerInfoRequestSerializationMapper mapper = new CustomerInfoRequest.CustomerInfoRequestSerializationMapper(this);
        return mapper.getSerializedObject();
    }

    protected String email;
    protected String phone;
    protected Number birthDateDay;
    protected String birthDateMonth;
    protected String birthDateYear;
    protected Order.Gender gender;

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

    public Number getBirthDateDay() {
        return birthDateDay;
    }

    public void setBirthDateDay(Number birthDateDay) {
        this.birthDateDay = birthDateDay;
    }

    public String getBirthDateMonth() {
        return birthDateMonth;
    }

    public void setBirthDateMonth(String birthDateMonth) {
        this.birthDateMonth = birthDateMonth;
    }

    public String getBirthDateYear() {
        return birthDateYear;
    }

    public void setBirthDateYear(String birthDateYear) {
        this.birthDateYear = birthDateYear;
    }

    public Order.Gender getGender() {
        return gender;
    }

    public void setGender(Order.Gender gender) {
        this.gender = gender;
    }

    public static class CustomerInfoRequestSerializationMapper extends AbstractSerializationMapper {

        protected CustomerInfoRequestSerializationMapper(CustomerInfoRequest request) {
            super(request);
        }

        @Override
        protected String getQueryString() {

            return super.getQueryString();
        }

        @Override
        protected Map<String, String> getSerializedObject() {

            return super.getSerializedObject();
        }
    }
}
