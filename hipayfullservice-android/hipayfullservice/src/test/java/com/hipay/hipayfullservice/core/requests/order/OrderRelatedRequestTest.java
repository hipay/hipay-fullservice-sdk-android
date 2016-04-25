package com.hipay.hipayfullservice.core.requests.order;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by nfillion on 22/02/16.
 */
public class OrderRelatedRequestTest {

    private OrderRelatedRequest orderRelatedRequest;

    @Before
    public void setUp() throws Exception {

        orderRelatedRequest = new OrderRelatedRequest();
    }

    @Test
    public void testGetters() throws Exception {

        orderRelatedRequest.getOrderId();
        orderRelatedRequest.getOperation();
        orderRelatedRequest.getShortDescription();
        orderRelatedRequest.getLongDescription();
        orderRelatedRequest.getCurrency();
        orderRelatedRequest.getAmount();
        orderRelatedRequest.getShipping();
        orderRelatedRequest.getTax();
        orderRelatedRequest.getClientId();
        orderRelatedRequest.getIpAddress();
        orderRelatedRequest.getAcceptScheme();
        orderRelatedRequest.getDeclineScheme();
        orderRelatedRequest.getPendingScheme();
        orderRelatedRequest.getExceptionScheme();
        orderRelatedRequest.getCancelScheme();
        orderRelatedRequest.getHTTPAccept();
        orderRelatedRequest.getHTTPUserAgent();
        orderRelatedRequest.getDeviceFingerprint();
        orderRelatedRequest.getLanguage();
        orderRelatedRequest.getCustomData();

        orderRelatedRequest.getCdata1();
        orderRelatedRequest.getCdata2();
        orderRelatedRequest.getCdata3();
        orderRelatedRequest.getCdata4();
        orderRelatedRequest.getCdata5();
        orderRelatedRequest.getCdata6();
        orderRelatedRequest.getCdata7();
        orderRelatedRequest.getCdata8();
        orderRelatedRequest.getCdata9();
        orderRelatedRequest.getCdata10();

        orderRelatedRequest.getCustomer();
        orderRelatedRequest.getShippingAddress();

    }

    @Test
    public void testSetters() throws Exception {


        orderRelatedRequest.setOrderId(null);
        orderRelatedRequest.setOperation(null);
        orderRelatedRequest.setShortDescription(null);
        orderRelatedRequest.setLongDescription(null);
        orderRelatedRequest.setCurrency(null);
        orderRelatedRequest.setAmount(null);
        orderRelatedRequest.setShipping(null);
        orderRelatedRequest.setTax(null);
        orderRelatedRequest.setClientId(null);
        orderRelatedRequest.setIpAddress(null);
        orderRelatedRequest.setAcceptScheme(null);
        orderRelatedRequest.setDeclineScheme(null);
        orderRelatedRequest.setPendingScheme(null);
        orderRelatedRequest.setExceptionScheme(null);
        orderRelatedRequest.setCancelScheme(null);
        orderRelatedRequest.setHTTPAccept(null);
        orderRelatedRequest.setHTTPUserAgent(null);
        orderRelatedRequest.setDeviceFingerprint(null);
        orderRelatedRequest.setLanguage(null);
        orderRelatedRequest.setCustomData(null);

        orderRelatedRequest.setCdata1(null);
        orderRelatedRequest.setCdata2(null);
        orderRelatedRequest.setCdata3(null);
        orderRelatedRequest.setCdata4(null);
        orderRelatedRequest.setCdata5(null);
        orderRelatedRequest.setCdata6(null);
        orderRelatedRequest.setCdata7(null);
        orderRelatedRequest.setCdata8(null);
        orderRelatedRequest.setCdata9(null);
        orderRelatedRequest.setCdata10(null);

        orderRelatedRequest.setCustomer(null);
        orderRelatedRequest.setShippingAddress(null);

    }
}
