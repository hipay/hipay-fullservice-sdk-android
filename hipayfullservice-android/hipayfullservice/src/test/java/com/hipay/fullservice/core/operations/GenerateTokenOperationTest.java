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
import static junit.framework.Assert.assertNull;

/**
 * Created by nfillion on 22/02/16.
 */
public class GenerateTokenOperationTest {

    private GenerateTokenOperation generateTokenOperation;

    @Mock
    Context context;

    @Mock
    Bundle bundle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {

        generateTokenOperation = new GenerateTokenOperation(context, bundle);
    }

    @Test
    public void testMethods() throws Exception {

        assertEquals(generateTokenOperation.concatUrl(), "token/create");
        assertEquals(generateTokenOperation.getRequestType(), AbstractHttpClient.HttpMethod.POST);
    }
}

