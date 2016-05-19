package com.hipay.hipayfullservice.core.requests.order;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nfillion on 22/02/16.
 */
public class OrderRequestTest {

    OrderRequest orderRequestTest;

    @Before
    public void setUp() throws Exception {

        orderRequestTest = new OrderRequest(new OrderRelatedRequest());
    }

    @Test
    public void testGetters() throws Exception {

        orderRequestTest.getPaymentProductCode();
    }

    @Test
    public void testSetters() throws Exception {

        orderRequestTest.setPaymentProductCode(null);
    }
}