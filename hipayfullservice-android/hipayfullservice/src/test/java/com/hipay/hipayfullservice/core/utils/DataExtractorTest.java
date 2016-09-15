package com.hipay.hipayfullservice.core.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * Created by nfillion on 09/02/16.
 */

public class DataExtractorTest {

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    JSONArray jsonArray;

    @Mock
    JSONObject jsonObject;

    @Test
    public void testDataExtractorGetters() throws Exception {

        String notFound = "not_found";

        String hello = "hello";
        String world = "world";

        when(jsonObject.optString(hello, null)).thenReturn(world);

        assertNull(DataExtractor.getStringFromField(jsonObject, notFound));
        assertEquals(DataExtractor.getStringFromField(jsonObject, hello), world);

        String one = "one";
        when(jsonObject.optInt(one, Integer.MIN_VALUE)).thenReturn(1);
        assertEquals(DataExtractor.getIntegerFromField(jsonObject, one).intValue(), 1);

        when(jsonObject.optInt(notFound, Integer.MIN_VALUE)).thenReturn(Integer.MIN_VALUE);
        assertNull(DataExtractor.getIntegerFromField(jsonObject, notFound));

        when(jsonArray.optInt(0, -1)).thenReturn(1);
        when(jsonArray.length()).thenReturn(1);

        assertEquals(DataExtractor.getIntegerFromField(jsonArray, 0).intValue(), 1);

        when(jsonObject.optJSONObject(hello)).thenReturn(jsonObject);
        assertEquals(DataExtractor.getJSONObjectFromField(jsonObject, hello), jsonObject);

        when(jsonArray.optJSONObject(0)).thenReturn(jsonObject);
        assertEquals(DataExtractor.getJSONObjectFromField(jsonArray, 0), jsonObject);

        when(jsonObject.optJSONArray(hello)).thenReturn(jsonArray);
        assertEquals(DataExtractor.getJSONArrayFromField(jsonObject, hello), jsonArray);

        when(jsonObject.opt(hello)).thenReturn(jsonObject);
        assertEquals(DataExtractor.getObjectFromField(jsonObject, hello), jsonObject);

        assertNull(DataExtractor.getDateFromField(jsonObject, notFound));
        assertNull(DataExtractor.getDateFromField(jsonArray, 0));
    }
}
