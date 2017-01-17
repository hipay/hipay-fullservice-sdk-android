package com.hipay.fullservice.core.requests.info;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.AbstractMapper;
import com.hipay.fullservice.core.requests.AbstractRequest;
import com.hipay.fullservice.core.serialization.AbstractSerializationMapper;
import com.hipay.fullservice.core.serialization.interfaces.AbstractSerialization;
import com.hipay.fullservice.core.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nfillion on 03/02/16.
 */
public class PersonalInfoRequest extends AbstractRequest {

    protected String firstname;
    protected String lastname;
    protected String streetAddress;
    protected String streetAddress2;
    protected String recipientInfo;
    protected String city;
    protected String state;
    protected String zipCode;
    protected String country;

    public PersonalInfoRequest() {
    }

    public static PersonalInfoRequest fromBundle(Bundle bundle) {

        PersonalInfoRequestMapper mapper = new PersonalInfoRequestMapper(bundle);
        return mapper.mappedObjectFromBundle();
    }

    public static PersonalInfoRequest fromJSONObject(JSONObject jsonObject) {

        PersonalInfoRequestMapper mapper = new PersonalInfoRequestMapper(jsonObject);
        return mapper.mappedObject();
    }

    public Bundle toBundle() {

        PersonalInfoRequest.PersonalInfoRequestSerializationMapper mapper = new PersonalInfoRequest.PersonalInfoRequestSerializationMapper(this);
        return mapper.getSerializedBundle();
    }

    public String getStringParameters() {

        PersonalInfoRequest.PersonalInfoRequestSerializationMapper mapper = new PersonalInfoRequest.PersonalInfoRequestSerializationMapper(this);
        return mapper.getQueryString();
    }

    public Map<String, String> getSerializedObject() {

        PersonalInfoRequest.PersonalInfoRequestSerializationMapper mapper = new PersonalInfoRequest.PersonalInfoRequestSerializationMapper(this);
        return mapper.getSerializedObject();
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDisplayName() {

        String firstname = this.getFirstname();
        String lastname = this.getLastname();

        if (firstname != null && lastname != null) {

            return new StringBuilder(firstname).append(" ").append(lastname).toString();

        } else if (firstname != null) {

            return firstname;

        } else if (lastname != null) {

            return lastname;
        }

        return null;
    }


    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getRecipientInfo() {
        return recipientInfo;
    }

    public void setRecipientInfo(String recipientInfo) {
        this.recipientInfo = recipientInfo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static class PersonalInfoRequestSerializationMapper extends AbstractSerializationMapper {

        protected PersonalInfoRequestSerializationMapper(PersonalInfoRequest request) {
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

    public static class PersonalInfoRequestSerialization extends AbstractSerialization {

        public PersonalInfoRequestSerialization(PersonalInfoRequest personalInfoRequest) {

            super(personalInfoRequest);
        }

        @Override
        public Map<String, String> getSerializedRequest() {

            PersonalInfoRequest personalInfoRequest = (PersonalInfoRequest)this.getModel();

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

            super.getSerializedBundle();

            PersonalInfoRequest personalInfoRequest = (PersonalInfoRequest)this.getModel();

            this.putStringForKey("firstname", personalInfoRequest.getFirstname());
            this.putStringForKey("lastname", personalInfoRequest.getLastname());
            this.putStringForKey("streetaddress", personalInfoRequest.getStreetAddress());
            this.putStringForKey("streetaddress2", personalInfoRequest.getStreetAddress2());
            this.putStringForKey("recipientinfo", personalInfoRequest.getRecipientInfo());
            this.putStringForKey("city", personalInfoRequest.getCity());
            this.putStringForKey("state", personalInfoRequest.getState());
            this.putStringForKey("zipcode", personalInfoRequest.getZipCode());
            this.putStringForKey("country", personalInfoRequest.getCountry());

            return this.getBundle();
        }

        @Override
        public String getQueryString() {

            return Utils.queryStringFromMap(this.getSerializedRequest());
        }
    }

    public static class PersonalInfoRequestMapper extends AbstractMapper {
        public PersonalInfoRequestMapper(Object rawData) {
            super(rawData);
        }

        @Override
        protected boolean isValid() {
            return true;
        }

        protected PersonalInfoRequest mappedObject() {

            PersonalInfoRequest object = new PersonalInfoRequest();

            object.setFirstname(this.getStringForKey("firstname"));
            object.setLastname(this.getStringForKey("lastname"));
            object.setStreetAddress(this.getStringForKey("streetaddress"));
            object.setStreetAddress2(this.getStringForKey("streetaddress2"));
            object.setRecipientInfo(this.getStringForKey("recipientinfo"));
            object.setCity(this.getStringForKey("city"));
            object.setState(this.getStringForKey("state"));
            object.setZipCode(this.getStringForKey("zipcode"));
            object.setCountry(this.getStringForKey("country"));

            return object;
        }

        @Override
        protected PersonalInfoRequest mappedObjectFromBundle() {

            PersonalInfoRequest object = new PersonalInfoRequest();

            object.setFirstname(this.getStringForKey("firstname"));
            object.setLastname(this.getStringForKey("lastname"));
            object.setStreetAddress(this.getStringForKey("streetaddress"));
            object.setStreetAddress2(this.getStringForKey("streetaddress2"));
            object.setRecipientInfo(this.getStringForKey("recipientinfo"));
            object.setCity(this.getStringForKey("city"));
            object.setState(this.getStringForKey("state"));
            object.setZipCode(this.getStringForKey("zipcode"));
            object.setCountry(this.getStringForKey("country"));

            return object;
        }

        @Override
        protected Object mappedObjectFromUri() {
            return null;
        }
    }
}
