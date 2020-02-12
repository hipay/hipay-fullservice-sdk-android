package com.hipay.fullservice.core.requests.payment;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by HiPay on 22/02/16.
 */
public class QiwiWalletPaymentMethodRequestTest {

    private QiwiWalletPaymentMethodRequest qiwiWalletPaymentMethodRequest;

    @Before
    public void setUp() throws Exception {

        qiwiWalletPaymentMethodRequest = new QiwiWalletPaymentMethodRequest();
    }

    @Test
    public void testGetters() throws Exception {

        qiwiWalletPaymentMethodRequest.getUsername();
    }

    @Test
    public void testSetters() throws Exception {

        qiwiWalletPaymentMethodRequest.setUsername(null);
    }
}