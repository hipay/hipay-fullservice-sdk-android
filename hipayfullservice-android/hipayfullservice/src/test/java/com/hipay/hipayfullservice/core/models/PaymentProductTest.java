package com.hipay.hipayfullservice.core.models;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by nfillion on 16/02/16.
 */
public class PaymentProductTest {

    private PaymentProduct paymentProduct;

    @Before
    public void createInstance() throws Exception {

        paymentProduct = new PaymentProduct();
    }

    @Test
    public void testGetters() throws Exception {

        paymentProduct.getCode();
        paymentProduct.getGroupedPaymentProductCodes();
        paymentProduct.getPaymentProductCategoryCode();
        paymentProduct.getPaymentProductDescription();
        paymentProduct.getPaymentProductId();
    }

    @Test
    public void testSetters() throws Exception {

        paymentProduct.setCode(null);
        paymentProduct.setGroupedPaymentProductCodes(null);
        paymentProduct.setPaymentProductCategoryCode(null);
        paymentProduct.setPaymentProductDescription(null);
        paymentProduct.setPaymentProductId(null);
    }

}