package com.hipay.fullservice.core.requests.info;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by HiPay on 22/02/16.
 */
public class CustomerInfoRequestTest {

    private CustomerInfoRequest customerInfoRequest;

    @Before
    public void setUp() throws Exception {

        customerInfoRequest = new CustomerInfoRequest();
    }

    @Test
    public void testGetters() throws Exception {

        customerInfoRequest.getEmail();
        customerInfoRequest.getPhone();
        customerInfoRequest.getBirthDateDay();
        customerInfoRequest.getBirthDateMonth();
        customerInfoRequest.getBirthDateYear();
        customerInfoRequest.getGender();
    }

    @Test
    public void testSetters() throws Exception {

        customerInfoRequest.setEmail(null);
        customerInfoRequest.setPhone(null);
        customerInfoRequest.setBirthDateDay(null);
        customerInfoRequest.setBirthDateMonth(null);
        customerInfoRequest.setBirthDateYear(null);
        customerInfoRequest.setGender(null);
    }
}