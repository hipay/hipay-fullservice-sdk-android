package com.hipay.fullservice.core.operations;

/**
 * Created by HiPay on 07/09/16.
 */

import static junit.framework.Assert.assertEquals;

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.network.AbstractHttpClient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Created by HiPay on 22/02/16.
 */
public class TransactionDetailsOperationTest {

    private TransactionDetailsOperation transactionDetailsOperation;

    @Mock
    Context context;

    @Mock
    Bundle bundle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {

        transactionDetailsOperation = new TransactionDetailsOperation(context, bundle);
    }

    @Test
    public void testMethods() throws Exception {

        assertEquals(transactionDetailsOperation.concatUrl(), "transaction");
        assertEquals(transactionDetailsOperation.getRequestType(), AbstractHttpClient.HttpMethod.GET);
    }
}
