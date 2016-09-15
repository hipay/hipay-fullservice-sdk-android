package com.hipay.hipayfullservice.core.requests.info;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.models.Order;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;
import com.hipay.hipayfullservice.core.utils.Utils;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by nfillion on 03/02/16.
 */
public class CustomerInfoRequest extends PersonalInfoRequest {

    public CustomerInfoRequest() {

        //this.setGender(Order.Gender.GenderUndefined);
    }

    public static CustomerInfoRequest fromBundle(Bundle bundle) {

        CustomerInfoRequestMapper mapper = new CustomerInfoRequestMapper(bundle);
        return mapper.mappedObjectFromBundle();
    }

    public static CustomerInfoRequest fromJSONObject(JSONObject jsonObject) {

        CustomerInfoRequestMapper mapper = new CustomerInfoRequestMapper(jsonObject);
        return mapper.mappedObject();
    }

    public Bundle toBundle() {

        CustomerInfoRequest.CustomerInfoRequestSerializationMapper mapper = new CustomerInfoRequest.CustomerInfoRequestSerializationMapper(this);
        return mapper.getSerializedBundle();
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
    protected Integer birthDateDay;
    protected Integer birthDateMonth;
    protected Integer birthDateYear;
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

        @Override
        protected Bundle getSerializedBundle() {

            return super.getSerializedBundle();
        }
    }

    public static class CustomerInfoRequestSerialization extends PersonalInfoRequestSerialization {

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

            CustomerInfoRequest customerInfoRequest = (CustomerInfoRequest)this.getModel();

            Map<String, String> personalInfoRequestMap = super.getSerializedRequest();

            personalInfoRequestMap.put("email", customerInfoRequest.getEmail());
            personalInfoRequestMap.put("phone", customerInfoRequest.getPhone());

            String birthDate = this.getBirthDate(customerInfoRequest.getBirthDateDay(), customerInfoRequest.getBirthDateMonth(), customerInfoRequest.getBirthDateYear());
            personalInfoRequestMap.put("birthdate", birthDate);

            Order.Gender gender = customerInfoRequest.getGender();
            if (gender != null && gender != Order.Gender.GenderUndefined) {
                personalInfoRequestMap.put("gender", String.valueOf(gender.getCharValue()));
            }

            return personalInfoRequestMap;
        }

        @Override
        public Bundle getSerializedBundle() {

            //super class put data into the bundle first
            super.getSerializedBundle();

            CustomerInfoRequest customerInfoRequest = (CustomerInfoRequest)this.getModel();

            this.putStringForKey("email", customerInfoRequest.getEmail());
            this.putStringForKey("phone", customerInfoRequest.getPhone());

            this.putIntForKey("birthDateDay", customerInfoRequest.getBirthDateDay());
            this.putIntForKey("birthDateMonth", customerInfoRequest.getBirthDateMonth());
            this.putIntForKey("birthDateYear", customerInfoRequest.getBirthDateYear());

            Order.Gender gender = customerInfoRequest.getGender();
            if (gender != null) {
                this.putStringForKey("gender", Character.toString(gender.getCharValue()));
            }

            return this.getBundle();
        }

        @Override
        public String getQueryString() {
            return Utils.queryStringFromMap(this.getSerializedRequest());
        }
    }

    public static class CustomerInfoRequestMapper extends PersonalInfoRequestMapper {
        public CustomerInfoRequestMapper(Object rawData) {
            super(rawData);
        }

        @Override
        protected boolean isValid() {

            if (getBehaviour() instanceof MapMapper) {
                return true;
            }

            return false;
        }

        @Override
        protected CustomerInfoRequest mappedObject() {

            //used in HostedPaymentPageMapper (see on iOS), using OrderMapper

            PersonalInfoRequest personalInfoRequest = super.mappedObject();
            CustomerInfoRequest object = this.customerFromPersonalRequest(personalInfoRequest);

            object.setEmail(this.getStringForKey("email"));
            object.setPhone(this.getStringForKey("phone"));
            object.setBirthDateDay(this.getIntegerForKey("birthDateDay"));
            object.setBirthDateMonth(this.getIntegerForKey("birthDateMonth"));
            object.setBirthDateYear(this.getIntegerForKey("birthDateYear"));

            String genderChar = this.getEnumCharForKey("gender");
            Order.Gender gender = Order.Gender.fromStringValue(genderChar);
            if (gender == null) {
                gender = Order.Gender.GenderUnknown;
            }
            object.setGender(gender);

            return object;
        }

        @Override
        protected CustomerInfoRequest mappedObjectFromBundle() {

            PersonalInfoRequest personalInfoRequest = super.mappedObjectFromBundle();
            CustomerInfoRequest object = this.customerFromPersonalRequest(personalInfoRequest);

            object.setEmail(this.getStringForKey("email"));
            object.setPhone(this.getStringForKey("phone"));
            object.setBirthDateDay(this.getIntegerForKey("birthDateDay"));
            object.setBirthDateMonth(this.getIntegerForKey("birthDateMonth"));
            object.setBirthDateYear(this.getIntegerForKey("birthDateYear"));

            String genderChar = this.getEnumCharForKey("gender");
            Order.Gender gender = Order.Gender.fromStringValue(genderChar);
            if (gender == null) {
                gender = Order.Gender.GenderUnknown;
            }
            object.setGender(gender);

            return object;
        }

        private CustomerInfoRequest customerFromPersonalRequest(PersonalInfoRequest personalInfoRequest) {

            CustomerInfoRequest customerInfoRequest = new CustomerInfoRequest();

            customerInfoRequest.setFirstname(personalInfoRequest.getFirstname());
            customerInfoRequest.setLastname(personalInfoRequest.getLastname());
            customerInfoRequest.setStreetAddress(personalInfoRequest.getStreetAddress());
            customerInfoRequest.setStreetAddress2(personalInfoRequest.getStreetAddress2());
            customerInfoRequest.setRecipientInfo(personalInfoRequest.getRecipientInfo());
            customerInfoRequest.setCity(personalInfoRequest.getCity());
            customerInfoRequest.setState(personalInfoRequest.getState());
            customerInfoRequest.setZipCode(personalInfoRequest.getZipCode());
            customerInfoRequest.setCountry(personalInfoRequest.getCountry());

            return customerInfoRequest;
        }
    }

}
