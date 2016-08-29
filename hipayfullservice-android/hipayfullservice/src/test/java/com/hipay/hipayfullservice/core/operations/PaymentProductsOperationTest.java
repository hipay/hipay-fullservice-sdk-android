package com.hipay.hipayfullservice.core.operations;

/**
 * Created by nfillion on 07/09/16.
 */

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.network.AbstractHttpClient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static junit.framework.Assert.assertEquals;

/**
 * Created by nfillion on 22/02/16.
 */
public class PaymentProductsOperationTest {

    private PaymentProductsOperation paymentProductsOperation;

    @Mock
    Context context;

    @Mock
    Bundle bundle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {

        paymentProductsOperation = new PaymentProductsOperation(context, bundle);
    }

    @Test
    public void testMethods() throws Exception {

        assertEquals(paymentProductsOperation.concatUrl(), "available-payment-products");
        assertEquals(paymentProductsOperation.getRequestType(), AbstractHttpClient.HttpMethod.GET);
    }
}
