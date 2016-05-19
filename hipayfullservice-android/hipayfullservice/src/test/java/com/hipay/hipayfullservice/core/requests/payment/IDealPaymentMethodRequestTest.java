package com.hipay.hipayfullservice.core.requests.payment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nfillion on 22/02/16.
 */
public class IDealPaymentMethodRequestTest {

    IDealPaymentMethodRequest iDealPaymentMethodRequest;

    @Before
    public void setUp() throws Exception {

        iDealPaymentMethodRequest = new IDealPaymentMethodRequest();
    }

    @Test
    public void testGetters() throws Exception {

        iDealPaymentMethodRequest.getIssuerBankId();
    }

    @Test
    public void testSetters() throws Exception {

        iDealPaymentMethodRequest.setIssuerBankId(null);
    }
}