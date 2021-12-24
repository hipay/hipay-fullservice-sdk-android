package com.hipay.fullservice.core.serialization.interfaces.payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import android.text.TextUtils;

import com.hipay.fullservice.core.requests.payment.SepaDirectDebitPaymentMethodRequest;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})

public class SepaDirectDebitPaymentMethodRequestSerializationTest {

    @Before
    public void setup() throws Exception {
        PowerMockito.mockStatic(TextUtils.class);
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    SepaDirectDebitPaymentMethodRequest sepaDirectDebitPaymentMethodRequest;

    @Test
    public void testMethods() throws Exception {

        SepaDirectDebitPaymentMethodRequestSerialization sepaDirectDebitPaymentMethodRequestSerialization = new SepaDirectDebitPaymentMethodRequestSerialization(sepaDirectDebitPaymentMethodRequest);
        assertNotNull(sepaDirectDebitPaymentMethodRequest);

        //always returns null
        assertNull(sepaDirectDebitPaymentMethodRequestSerialization.getSerializedBundle());

        when(sepaDirectDebitPaymentMethodRequest.getFirstname()).thenReturn("John");
        when(sepaDirectDebitPaymentMethodRequest.getLastname()).thenReturn("Doe");
        when(sepaDirectDebitPaymentMethodRequest.getIban()).thenReturn("FR7630006000011234567890189");

        Map<String, String> testMap = new HashMap<>(3);
        testMap.put("firstname", "John");
        testMap.put("lastname", "Doe");
        testMap.put("iban", "FR7630006000011234567890189");

        //test getSerializedRequest
        assertEquals(sepaDirectDebitPaymentMethodRequestSerialization.getSerializedRequest(), testMap);

        List<String> parameters = new ArrayList<>(3);
        parameters.add("firstname=John");
        parameters.add("lastname=Doe");
        parameters.add("iban=FR7630006000011234567890189");

        Collections.sort(parameters);

        //TextUtils transforms the list in String
        String result = "firstname=John&lastname=Doe&iban=FR7630006000011234567890189";

        BDDMockito.given(TextUtils.join("&", parameters)).willReturn(result);

        assertEquals(sepaDirectDebitPaymentMethodRequestSerialization.getQueryString(), result);

    }
}
