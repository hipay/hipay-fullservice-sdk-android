package com.hipay.fullservice.core.operations;

/**
 * Created by nfillion on 07/09/16.
 */

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.network.AbstractHttpClient;

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
public class TransactionsDetailsOperationTest {

    private TransactionsDetailsOperation transactionsDetailsOperation;

    @Mock
    Context context;

    @Mock
    Bundle bundle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {

        transactionsDetailsOperation = new TransactionsDetailsOperation(context, bundle);
    }

    @Test
    public void testMethods() throws Exception {

        assertEquals(transactionsDetailsOperation.concatUrl(), "transaction");
        assertEquals(transactionsDetailsOperation.getRequestType(), AbstractHttpClient.HttpMethod.GET);
    }
}
