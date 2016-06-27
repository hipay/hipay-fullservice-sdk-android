package com.hipay.hipayfullservice.core.requests.info;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by nfillion on 22/02/16.
 */
public class PersonalInfoRequestTest {

    PersonalInfoRequest personalInfoRequest;

    @Before
    public void setUp() throws Exception {

        personalInfoRequest = new PersonalInfoRequest();
    }

    @Test
    public void testGetters() throws Exception {

        personalInfoRequest.getDisplayName();
        personalInfoRequest.getFirstname();
        personalInfoRequest.getLastname();
        personalInfoRequest.getStreetAddress();
        personalInfoRequest.getStreetAddress2();
        personalInfoRequest.getRecipientInfo();
        personalInfoRequest.getCity();
        personalInfoRequest.getState();
        personalInfoRequest.getZipCode();
        personalInfoRequest.getCountry();

    }

    @Test
    public void testSetters() throws Exception {

        personalInfoRequest.setFirstname(null);
        personalInfoRequest.setLastname(null);
        personalInfoRequest.setStreetAddress(null);
        personalInfoRequest.setStreetAddress2(null);
        personalInfoRequest.setRecipientInfo(null);
        personalInfoRequest.setCity(null);
        personalInfoRequest.setState(null);
        personalInfoRequest.setZipCode(null);
        personalInfoRequest.setCountry(null);

    }
}