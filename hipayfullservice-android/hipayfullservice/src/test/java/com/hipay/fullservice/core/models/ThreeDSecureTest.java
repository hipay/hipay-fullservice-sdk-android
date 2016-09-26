package com.hipay.fullservice.core.models;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by nfillion on 16/02/16.
 */
public class ThreeDSecureTest {

    private ThreeDSecure threeDSecure;

    @Before
    public void createInstance() throws Exception {

        threeDSecure = new ThreeDSecure();
    }

    @Test
    public void testGetters() throws Exception {

        threeDSecure.getAuthenticationMessage();
        threeDSecure.getAuthenticationStatus();
        threeDSecure.getAuthenticationToken();
        threeDSecure.getEnrollmentMessage();
        threeDSecure.getEnrollmentStatus();
        threeDSecure.getXid();
    }

    @Test
    public void testSetters() throws Exception {

        threeDSecure.setAuthenticationMessage(null);
        threeDSecure.setAuthenticationStatus(null);
        threeDSecure.setAuthenticationToken(null);
        threeDSecure.setEnrollmentMessage(null);
        threeDSecure.setEnrollmentStatus(null);
        threeDSecure.setXid(null);
    }
}