package com.hipay.fullservice.core.requests.payment;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by HiPay on 22/02/16.
 */
public class CardTokenPaymentMethodRequestTest {

    CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest;

    @Before
    public void setUp() throws Exception {

        cardTokenPaymentMethodRequest = new CardTokenPaymentMethodRequest();
    }

    @Test
    public void testGetters() throws Exception {

        cardTokenPaymentMethodRequest.getCardToken();
        cardTokenPaymentMethodRequest.getEci();
        cardTokenPaymentMethodRequest.getAuthenticationIndicator();
    }

    @Test
    public void testSetters() throws Exception {

        cardTokenPaymentMethodRequest.setCardToken(null);
        cardTokenPaymentMethodRequest.setEci(null);
        cardTokenPaymentMethodRequest.setAuthenticationIndicator(null);
    }
}