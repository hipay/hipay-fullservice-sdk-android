package com.hipay.hipayfullservice.core.models;

import com.hipay.hipayfullservice.core.mapper.AbstractMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;

import org.json.JSONObject;

/**
 * Created by nfillion on 25/01/16.
 */
public class PersonalInformation extends AbstractModel {

    protected String firstname;
    protected String lastname;
    protected String streetAddress;
    protected String locality;
    protected String postalCode;
    protected String country;
    protected String msisdn;
    protected String phone;
    protected String phoneOperator;
    protected String email;

    public PersonalInformation() {
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public String getPhoneOperator() {
        return phoneOperator;
    }

    public void setPhoneOperator(String phoneOperator) {
        this.phoneOperator = phoneOperator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class PersonalInformationMapper extends AbstractMapper {
        public PersonalInformationMapper(Object rawData) {
            super(rawData);
        }

        @Override
        protected boolean isValid() {

            if (this.getBehaviour() instanceof MapMapper) {

                if (this.getStringForKey("email") != null) return true;
            }

            return false;
        }

        protected Object mappedObject() {

            PersonalInformation object = new PersonalInformation();

            object.setFirstname(this.getStringForKey("firstname"));
            object.setLastname(this.getStringForKey("lastname"));
            object.setStreetAddress(this.getStringForKey("streetAddress"));
            object.setLocality(this.getStringForKey("streetLocality"));
            object.setPostalCode(this.getStringForKey("postalCode"));
            object.setCountry(this.getStringForKey("country"));
            object.setMsisdn(this.getStringForKey("msisdn"));
            object.setPhone(this.getStringForKey("phone"));
            object.setPhoneOperator(this.getStringForKey("phoneOperator"));
            object.setEmail(this.getStringForKey("email"));

            return object;

        }

        @Override
        protected Object mappedObjectFromBundle() {
            return null;
        }
    }

}
