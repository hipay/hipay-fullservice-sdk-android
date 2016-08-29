package com.hipay.hipayfullservice.core.mapper;

/**
 * Created by nfillion on 09/09/16.
 */

import android.text.TextUtils;

import com.hipay.hipayfullservice.core.models.HostedPaymentPage;

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

import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class HostedPaymentPageMapperTest {

    @Before
    public void setup() throws Exception {
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    HostedPaymentPage hostedPaymentPage;

    @Mock
    JSONObject jsonObject;

    @Test
    public void testMethods() throws Exception {

        HostedPaymentPageMapper hostedPaymentPageMapper = new HostedPaymentPageMapper(jsonObject);

        when(jsonObject.optString("cdata1", null)).thenReturn("hello data 1");
        when(jsonObject.optString("cdata2", null)).thenReturn("hello data 2");
        when(jsonObject.optString("cdata3", null)).thenReturn("hello data 3");
        when(jsonObject.optString("cdata4", null)).thenReturn("hello data 4");
        when(jsonObject.optString("cdata5", null)).thenReturn("hello data 5");
        when(jsonObject.optString("cdata6", null)).thenReturn("hello data 6");
        when(jsonObject.optString("cdata7", null)).thenReturn("hello data 7");
        when(jsonObject.optString("cdata8", null)).thenReturn("hello data 8");
        when(jsonObject.optString("cdata9", null)).thenReturn("hello data 9");
        when(jsonObject.optString("cdata10", null)).thenReturn("hello data 10");

        when(jsonObject.optString("forwardUrl", null)).thenReturn("http://www.example.com");
        when(jsonObject.opt("test")).thenReturn("false");
        when(jsonObject.optString("mid", null)).thenReturn("hello mid");

        HostedPaymentPage hostedPaymentPageCompare = new HostedPaymentPage();
        hostedPaymentPageCompare.setCdata1("hello data 1");
        hostedPaymentPageCompare.setCdata2("hello data 2");
        hostedPaymentPageCompare.setCdata3("hello data 3");
        hostedPaymentPageCompare.setCdata4("hello data 4");
        hostedPaymentPageCompare.setCdata5("hello data 5");
        hostedPaymentPageCompare.setCdata6("hello data 6");
        hostedPaymentPageCompare.setCdata7("hello data 7");
        hostedPaymentPageCompare.setCdata8("hello data 8");
        hostedPaymentPageCompare.setCdata9("hello data 9");
        hostedPaymentPageCompare.setCdata10("hello data 10");

        hostedPaymentPageCompare.setForwardUrl(new URL("http://www.example.com"));
        hostedPaymentPageCompare.setTest(Boolean.FALSE);
        hostedPaymentPageCompare.setMid("hello mid");

        HostedPaymentPage hostedPaymentPageTest = hostedPaymentPageMapper.mappedObject();

        assertEquals(hostedPaymentPageTest.getCdata1(), hostedPaymentPageCompare.getCdata1());
        assertEquals(hostedPaymentPageTest.getCdata2(), hostedPaymentPageCompare.getCdata2());
        assertEquals(hostedPaymentPageTest.getCdata3(), hostedPaymentPageCompare.getCdata3());
        assertEquals(hostedPaymentPageTest.getCdata4(), hostedPaymentPageCompare.getCdata4());
        assertEquals(hostedPaymentPageTest.getCdata5(), hostedPaymentPageCompare.getCdata5());
        assertEquals(hostedPaymentPageTest.getCdata6(), hostedPaymentPageCompare.getCdata6());
        assertEquals(hostedPaymentPageTest.getCdata7(), hostedPaymentPageCompare.getCdata7());
        assertEquals(hostedPaymentPageTest.getCdata8(), hostedPaymentPageCompare.getCdata8());
        assertEquals(hostedPaymentPageTest.getCdata9(), hostedPaymentPageCompare.getCdata9());
        assertEquals(hostedPaymentPageTest.getCdata10(), hostedPaymentPageCompare.getCdata10());

        assertEquals(hostedPaymentPageTest.getForwardUrl(), hostedPaymentPageCompare.getForwardUrl());
        assertEquals(hostedPaymentPageTest.getTest(), hostedPaymentPageCompare.getTest());
        assertEquals(hostedPaymentPageTest.getMid(), hostedPaymentPageCompare.getMid());
    }
}
