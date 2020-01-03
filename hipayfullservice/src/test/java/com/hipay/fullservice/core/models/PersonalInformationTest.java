package com.hipay.fullservice.core.models;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by HiPay on 16/02/16.
 */
public class PersonalInformationTest {

    private PersonalInformation personalInformation;

    @Before
    public void createInstance() throws Exception {

        personalInformation = new PersonalInformation();
    }

    @Test
    public void testGetters() throws Exception {

        personalInformation.getFirstname();
        personalInformation.getLastname();
        personalInformation.getStreetAddress();
        personalInformation.getLocality();
        personalInformation.getPostalCode();
        personalInformation.getCountry();
        personalInformation.getMsisdn();
        personalInformation.getPhone();
        personalInformation.getPhoneOperator();
        personalInformation.getEmail();
    }

    @Test
    public void testSetters() throws Exception {

        personalInformation.setFirstname(null);
        personalInformation.setLastname(null);
        personalInformation.setStreetAddress(null);
        personalInformation.setLocality(null);
        personalInformation.setPostalCode(null);
        personalInformation.setCountry(null);
        personalInformation.setMsisdn(null);
        personalInformation.setPhone(null);
        personalInformation.setPhoneOperator(null);
        personalInformation.setEmail(null);
    }
}