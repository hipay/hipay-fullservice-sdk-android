package com.hipay.fullservice.core.serialization;
/**
 * Created by nfillion on 08/09/16.
 */

import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.serialization.interfaces.PaymentProductSerialization;

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

public class PaymentProductSerializationTest {

    @Before
    public void setup() throws Exception {
    }

    @Mock
    PaymentProduct paymentProduct;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testMethods() throws Exception {

        PaymentProductSerialization paymentProductSerialization = new PaymentProductSerialization(paymentProduct);
        assertNull(paymentProductSerialization.getQueryString());
        assertNull(paymentProductSerialization.getSerializedRequest());
    }
}

