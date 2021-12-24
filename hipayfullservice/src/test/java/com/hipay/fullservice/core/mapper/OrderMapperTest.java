package com.hipay.fullservice.core.mapper;

/**
 * Created by HiPay on 09/09/16.
 */

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

import android.text.TextUtils;

import com.hipay.fullservice.core.models.Order;

import org.json.JSONObject;
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

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})

public class OrderMapperTest {

    @Before
    public void setup() throws Exception {

        PowerMockito.mockStatic(TextUtils.class);
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    JSONObject jsonObject;

    @Test
    public void testMethods() throws Exception {

        OrderMapper orderMapper = new OrderMapper(jsonObject);

        when(jsonObject.optString("currency", null)).thenReturn("EUR");
        when(jsonObject.optString("customerId", null)).thenReturn("hello customer id");
        when(jsonObject.optString("language", null)).thenReturn("hello fr");
        when(jsonObject.optString("id", null)).thenReturn("hello order id");
        when(jsonObject.opt("attempts")).thenReturn("2");
        when(jsonObject.opt("amount")).thenReturn("399.99f");
        when(jsonObject.opt("shipping")).thenReturn("3.5f");
        when(jsonObject.opt("tax")).thenReturn("2.8");
        when(jsonObject.opt("decimals")).thenReturn("8");
        when(jsonObject.optString("gender", null)).thenReturn("M");
        when(jsonObject.opt("dateCreated")).thenReturn("try to put some date");

        Order orderCompare = new Order();

        orderCompare.setCurrency("EUR");
        orderCompare.setCustomerId("hello customer id");
        orderCompare.setLanguage("hello fr");
        orderCompare.setOrderId("hello order id");
        orderCompare.setAttempts(2);
        orderCompare.setAmount(399.99f);
        orderCompare.setShipping(3.5f);
        orderCompare.setTax(2.8f);
        orderCompare.setDecimals(8);
        orderCompare.setGender(Order.Gender.GenderMale);

        BDDMockito.given(TextUtils.isDigitsOnly("2")).willReturn(true);
        BDDMockito.given(TextUtils.isDigitsOnly("8")).willReturn(true);

        BDDMockito.given(TextUtils.isEmpty(null)).willReturn(true);

        Order orderTest = orderMapper.mappedObject();

        assertEquals(orderTest.getCurrency(), orderCompare.getCurrency());
        assertEquals(orderTest.getCustomerId(), orderCompare.getCustomerId());
        assertEquals(orderTest.getLanguage(), orderCompare.getLanguage());
        assertEquals(orderTest.getOrderId(), orderCompare.getOrderId());
        assertEquals(orderTest.getAttemps(), orderCompare.getAttemps());
        assertEquals(orderTest.getAmount(), orderCompare.getAmount());
        assertEquals(orderTest.getShipping(), orderCompare.getShipping());
        assertEquals(orderTest.getTax(), orderCompare.getTax());
        assertEquals(orderTest.getDecimals(), orderCompare.getDecimals());
        assertEquals(orderTest.getGender(), orderCompare.getGender());
    }
}

