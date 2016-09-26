package com.hipay.fullservice.core.serialization.interfaces.securevault;

import android.text.TextUtils;

import com.hipay.fullservice.core.requests.securevault.SecureVaultRequest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
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
 * Created by nfillion on 09/02/16.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})

public class SecureVaultRequestSerializationTest {

    @Before
    public void setup() throws Exception {

        PowerMockito.mockStatic(TextUtils.class);
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    SecureVaultRequest secureVaultRequest;

    @Test
    public void testMethods() throws Exception {

        SecureVaultRequestSerialization secureVaultRequestSerialization = new SecureVaultRequestSerialization(secureVaultRequest);
        assertNotNull(secureVaultRequestSerialization);

        //always returns null
        assertNull(secureVaultRequestSerialization.getSerializedBundle());

        when(secureVaultRequest.getCardNumber()).thenReturn("1234");
        when(secureVaultRequest.getCardExpiryMonth()).thenReturn("12");
        when(secureVaultRequest.getCardHolder()).thenReturn("Martin Dupont");
        when(secureVaultRequest.getCardCVC()).thenReturn("123");
        when(secureVaultRequest.getMultiUse()).thenReturn(Boolean.TRUE);
        when(secureVaultRequest.getCardExpiryYear()).thenReturn("20");

        Map<String, String> testMap = new HashMap<>(6);
        testMap.put("card_expiry_month", "12");
        testMap.put("card_holder", "Martin Dupont");
        testMap.put("cvc", "123");
        testMap.put("card_expiry_year", "20");
        testMap.put("card_number", "1234");
        testMap.put("multi_use", "1");

        //test getSerializedRequest
        assertEquals(secureVaultRequestSerialization.getSerializedRequest(), testMap);

        List<String> parameters = new ArrayList<>(6);
        parameters.add("card_expiry_month=12");
        parameters.add("card_expiry_year=20");
        parameters.add("card_holder=Martin%20Dupont");
        parameters.add("card_number=1234");
        parameters.add("cvc=123");
        parameters.add("multi_use=1");

        // alphabet order
        Collections.sort(parameters);

        //TextUtils transforms the list in String
        //String result = "card_expiry_month=12&card_expiry_month=12&cvc=123&card_number=1234&multi_use=1&card_expiry_year=20";
        String result = "hello";
        //Mockito.any();
        BDDMockito.given(TextUtils.join(Mockito.eq("&"), Mockito.eq(parameters))).willReturn(result);
        assertEquals(secureVaultRequestSerialization.getQueryString(), result);

    }
}
