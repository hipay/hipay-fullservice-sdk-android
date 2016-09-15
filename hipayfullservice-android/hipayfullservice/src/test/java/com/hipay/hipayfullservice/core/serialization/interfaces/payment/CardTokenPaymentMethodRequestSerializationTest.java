package com.hipay.hipayfullservice.core.serialization.interfaces.payment;

import android.text.TextUtils;

import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * Created by nfillion on 01/09/16.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})

public class CardTokenPaymentMethodRequestSerializationTest {

    @Before
    public void setup() throws Exception {
        PowerMockito.mockStatic(TextUtils.class);
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest;

    @Test
    public void testMethods() throws Exception {

        CardTokenPaymentMethodRequestSerialization cardTokenPaymentMethodRequestSerialization = new CardTokenPaymentMethodRequestSerialization(cardTokenPaymentMethodRequest);
        assertNotNull(cardTokenPaymentMethodRequest);

        //always returns null
        assertNull(cardTokenPaymentMethodRequestSerialization.getSerializedBundle());

        when(cardTokenPaymentMethodRequest.getCardToken()).thenReturn("123cardtoken123");
        when(cardTokenPaymentMethodRequest.getEci()).thenReturn(Transaction.ECI.InstallmentPayment);
        when(cardTokenPaymentMethodRequest.getAuthenticationIndicator()).thenReturn(CardTokenPaymentMethodRequest.AuthenticationIndicator.Mandatory);

        Map<String, String> testMap = new HashMap<>(3);
        testMap.put("cardtoken", "123cardtoken123");
        testMap.put("eci", "3");
        testMap.put("authentication_indicator", "2");

        //test getSerializedRequest
        assertEquals(cardTokenPaymentMethodRequestSerialization.getSerializedRequest(), testMap);

        List<String> parameters = new ArrayList<>(3);
        parameters.add("authentication_indicator=2");
        parameters.add("cardtoken=123cardtoken123");
        parameters.add("eci=3");

        Collections.sort(parameters);

        //TextUtils transforms the list in String
        String result = "authentication_indicator=2&cardtoken=123cardtoken123&eci=3";

        BDDMockito.given(TextUtils.join("&", parameters)).willReturn(result);

        assertEquals(cardTokenPaymentMethodRequestSerialization.getQueryString(), result);

    }
}
