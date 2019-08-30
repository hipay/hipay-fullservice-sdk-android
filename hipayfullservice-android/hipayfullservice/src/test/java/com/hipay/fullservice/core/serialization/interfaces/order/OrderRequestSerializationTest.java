package com.hipay.fullservice.core.serialization.interfaces.order;


import android.text.TextUtils;

import com.hipay.fullservice.core.requests.order.OrderRequest;

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
import static org.mockito.Mockito.after;
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

    @Mock
    OrderRequest orderRequest;

    @Test
    public void testMethods() throws Exception {

        OrderRequestSerialization orderRequestSerialization = new OrderRequestSerialization(orderRequest);
        assertNotNull(orderRequestSerialization);

        when(orderRequest.getDeviceChannel()).thenReturn(1);
        when(orderRequest.getPaymentProductCode()).thenReturn("cb");

        //to avoid superclass problems
        when(orderRequest.getShipping()).thenReturn(null);
        when(orderRequest.getAmount()).thenReturn(null);
        when(orderRequest.getTax()).thenReturn(null);

        Map<String, String> testMap = new HashMap<>(2);
        testMap.put("account_info", "{\"shipping\":{\"name_indicator\":2},\"purchase\":{\"card_stored_24h\":0}}");
        testMap.put("device_channel", "1");
        testMap.put("payment_product", "cb");

        assertEquals(orderRequestSerialization.getSerializedRequest(), testMap);

        List<String> parameters = new ArrayList<>(2);
        parameters.add("payment_product=payment%20product%20code");
        parameters.add("account_info={\"shipping\": {\"name_indicator\": 2}}");

        Collections.sort(parameters);

        //TextUtils transforms the list in String
        StringBuilder stringBuilder = new StringBuilder(parameters.get(0)).append("&").append(parameters.get(1));

        BDDMockito.given(TextUtils.join("&", parameters)).willReturn(stringBuilder.toString());

        //assertEquals(orderRequestSerialization.getQueryString(), stringBuilder.toString());
    }
}

