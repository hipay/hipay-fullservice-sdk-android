package com.hipay.fullservice.core.models;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.PersonalInformationMapper;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by nfillion on 25/01/16.
 */
public class PersonalInformation extends AbstractModel  implements Serializable {

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


    public static PersonalInformation fromJSONObject(JSONObject object) {

        PersonalInformationMapper mapper = new PersonalInformationMapper(object);
        return mapper.mappedObject();
    }

    public static PersonalInformation fromBundle(Bundle bundle) {

        PersonalInformationMapper mapper = new PersonalInformationMapper(bundle);
        return mapper.mappedObject();
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

}
