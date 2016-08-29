package com.hipay.hipayfullservice.core.serialization.interfaces.order;


import android.text.TextUtils;

import com.hipay.hipayfullservice.core.requests.order.OrderRequest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by nfillion on 01/09/16.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})

public class OrderRequestSerializationTest {

    @Before
    public void setup() throws Exception {
        PowerMockito.mockStatic(TextUtils.class);
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    OrderRequest orderRequest;

    @Test
    public void testMethods() throws Exception {

        OrderRequestSerialization orderRequestSerialization = new OrderRequestSerialization(orderRequest);
        assertNotNull(orderRequestSerialization);

        //always returns null
        //assertNull(cardTokenPaymentMethodRequestSerialization.getSerializedBundle());

        when(orderRequest.getPaymentProductCode()).thenReturn("payment product code");

        //to avoid superclass problems
        when(orderRequest.getShipping()).thenReturn(null);
        when(orderRequest.getAmount()).thenReturn(null);
        when(orderRequest.getTax()).thenReturn(null);

        Map<String, String> testMap = new HashMap<>(1);
        testMap.put("payment_product", "payment product code");

        //test getSerializedRequest
        assertEquals(orderRequestSerialization.getSerializedRequest(), testMap);

        List<String> parameters = new ArrayList<>(1);
        parameters.add("payment_product=payment%20product%20code");

        Collections.sort(parameters);

        //TextUtils transforms the list in String
        String result = parameters.get(0);

        BDDMockito.given(TextUtils.join("&", parameters)).willReturn(result);

        assertEquals(orderRequestSerialization.getQueryString(), result);

    }
}

