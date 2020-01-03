package com.hipay.fullservice.core.serialization.interfaces.order;

/**
 * Created by HiPay on 08/09/16.
 */

import com.hipay.fullservice.core.models.Order;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertNull;

/**
 * Created by HiPay on 01/09/16.
 */

@RunWith(PowerMockRunner.class)

public class OrderSerializationTest {

    @Before
    public void setup() throws Exception {
    }

    @Mock
    Order order;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testMethods() throws Exception {

        OrderSerialization orderSerialization = new OrderSerialization(order);
        assertNull(orderSerialization.getQueryString());
        assertNull(orderSerialization.getSerializedRequest());
    }
}
