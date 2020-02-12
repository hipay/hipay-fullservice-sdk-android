package com.hipay.fullservice.core.models;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by HiPay on 16/02/16.
 */
public class TransactionTest {

    private Transaction transaction;

    @Before
    public void createInstance() throws Exception {
        transaction = new Transaction();
    }

    @Test
    public void testGetters() throws Exception {

        transaction.getState();
        transaction.getReason();
        transaction.getForwardUrl();
        transaction.getAttemptId();
        transaction.getReferenceToPay();
        transaction.getIpAddress();
        transaction.getIpCountry();
        transaction.getDeviceId();

        transaction.getAvsResult();
        transaction.getCvcResult();
        transaction.getEci();

        transaction.getPaymentProduct();
        transaction.getThreeDSecure();
        transaction.getFraudScreening();
        transaction.getOrder();

        transaction.getCdata1();
        transaction.getCdata2();
        transaction.getCdata3();
        transaction.getCdata4();
        transaction.getCdata5();
        transaction.getCdata6();
        transaction.getCdata7();
        transaction.getCdata8();
        transaction.getCdata9();
        transaction.getCdata10();
    }

    @Test
    public void testSetters() throws Exception {

        transaction.setState(null);
        transaction.setReason(null);
        transaction.setForwardUrl(null);
        transaction.setAttemptId(null);
        transaction.setReferenceToPay(null);
        transaction.setIpAddress(null);
        transaction.setIpCountry(null);
        transaction.setDeviceId(null);

        transaction.setAvsResult(null);
        transaction.setCvcResult(null);
        transaction.setEci(null);

        transaction.setPaymentProduct(null);
        transaction.setThreeDSecure(null);
        transaction.setFraudScreening(null);
        transaction.setOrder(null);

        transaction.setCdata1(null);
        transaction.setCdata2(null);
        transaction.setCdata3(null);
        transaction.setCdata4(null);
        transaction.setCdata5(null);
        transaction.setCdata6(null);
        transaction.setCdata7(null);
        transaction.setCdata8(null);
        transaction.setCdata9(null);
        transaction.setCdata10(null);
    }
}