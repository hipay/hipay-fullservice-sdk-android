package com.hipay.hipayfullservice.core.serialization;
/**
 * Created by nfillion on 08/09/16.
 */

import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.serialization.interfaces.TransactionSerialization;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static junit.framework.Assert.assertNull;

/**
 * Created by nfillion on 01/09/16.
 */

public class TransactionSerializationTest {

    @Before
    public void setup() throws Exception {
    }

    @Mock
    Transaction transaction;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testMethods() throws Exception {

        TransactionSerialization transactionSerialization = new TransactionSerialization(transaction);
        assertNull(transactionSerialization.getQueryString());
        assertNull(transactionSerialization.getSerializedRequest());
    }
}

