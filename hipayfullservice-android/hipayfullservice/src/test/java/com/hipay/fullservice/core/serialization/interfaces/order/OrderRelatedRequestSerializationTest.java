package com.hipay.fullservice.core.serialization.interfaces.order;

import android.text.TextUtils;

import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.core.utils.Utils;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by nfillion on 01/09/16.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class, Utils.class})

public class OrderRelatedRequestSerializationTest {

    @Before
    public void setup() throws Exception {
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.mockStatic(Utils.class);
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest;

    @Mock
    JSONObject jsonObject;

    @Test
    public void testDataExtractorGetters() throws Exception {

        //OrderRelatedRequestSerialization is abstract, need to test it with a concrete one like OrderRequest or PaymentPageRequest one.
    }
}
