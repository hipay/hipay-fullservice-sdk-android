package com.hipay.hipayfullservice.core.requests.info;

import com.hipay.hipayfullservice.core.requests.AbstractRequest;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;

import java.util.Map;

/**
 * Created by nfillion on 03/02/16.
 */
public class PersonalInfoRequest extends AbstractRequest {

    protected String firstname;
    protected String lastname;
    protected String displayName;
    protected String streetAddress;
    protected String streetAddress2;
    protected String recipientInfo;
    protected String city;
    protected String state;
    protected String zipCode;
    protected String country;

    public PersonalInfoRequest() {
    }

    public String getStringParameters() {

        PersonalInfoRequest.PersonalInfoRequestSerializationMapper mapper = new PersonalInfoRequest.PersonalInfoRequestSerializationMapper(this);
        return mapper.getQueryString();
    }

    public Map<String, String> getSerializedObject() {

        PersonalInfoRequest.PersonalInfoRequestSerializationMapper mapper = new PersonalInfoRequest.PersonalInfoRequestSerializationMapper(this);
        return mapper.getSerializedObject();
    }

    protected String displayName() {

        String firstname = this.getFirstname();
        String lastname = this.getLastname();

        //TODO strim strings

        if (firstname != null && lastname != null) {

            return new StringBuilder(firstname).append(" ").append(lastname).toString();

        } else if (firstname != null) {

            return firstname;

        } else if (lastname != null) {

            return lastname;
        }

        return null;
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
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
    }
}
