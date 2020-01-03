package com.hipay.fullservice.core.models;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by HiPay on 16/02/16.
 */
public class HostedPaymentPageTest {

    private HostedPaymentPage hostedPaymentPage;

    @Before
    public void createInstance() throws Exception {

        hostedPaymentPage = new HostedPaymentPage();
    }

    @Test
    public void testGetters() throws Exception {

        hostedPaymentPage.getTest();
        hostedPaymentPage.getMid();
        hostedPaymentPage.getForwardUrl();
        hostedPaymentPage.getOrder();
        hostedPaymentPage.getCdata1();
        hostedPaymentPage.getCdata2();
        hostedPaymentPage.getCdata3();
        hostedPaymentPage.getCdata4();
        hostedPaymentPage.getCdata5();
        hostedPaymentPage.getCdata6();
        hostedPaymentPage.getCdata7();
        hostedPaymentPage.getCdata8();
        hostedPaymentPage.getCdata9();
        hostedPaymentPage.getCdata10();
    }

    @Test
    public void testSetters() throws Exception {

        hostedPaymentPage.setTest(null);
        hostedPaymentPage.setMid(null);
        hostedPaymentPage.setForwardUrl(null);
        hostedPaymentPage.setOrder(null);
        hostedPaymentPage.setCdata1(null);
        hostedPaymentPage.setCdata2(null);
        hostedPaymentPage.setCdata3(null);
        hostedPaymentPage.setCdata4(null);
        hostedPaymentPage.setCdata5(null);
        hostedPaymentPage.setCdata6(null);
        hostedPaymentPage.setCdata7(null);
        hostedPaymentPage.setCdata8(null);
        hostedPaymentPage.setCdata9(null);
        hostedPaymentPage.setCdata10(null);

    }
}