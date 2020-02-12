package com.hipay.fullservice.core.requests.payment;

import org.junit.Before;
import org.junit.Test;

public class SepaDirectDebitPaymentMethodRequestTest {

    private SepaDirectDebitPaymentMethodRequest sepaDirectDebitPaymentMethodRequest;

    @Before
    public void setUp() throws Exception {

        sepaDirectDebitPaymentMethodRequest = new SepaDirectDebitPaymentMethodRequest();
    }

    @Test
    public void testGetters() throws Exception {

        sepaDirectDebitPaymentMethodRequest.getFirstname();
        sepaDirectDebitPaymentMethodRequest.getLastname();
        sepaDirectDebitPaymentMethodRequest.getIban();
    }

    @Test
    public void testSetters() throws Exception {

        sepaDirectDebitPaymentMethodRequest.setFirstname(null);
        sepaDirectDebitPaymentMethodRequest.setLastname(null);
        sepaDirectDebitPaymentMethodRequest.setIban(null);

    }
}
