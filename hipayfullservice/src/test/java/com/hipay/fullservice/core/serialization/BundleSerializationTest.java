package com.hipay.fullservice.core.serialization;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by HiPay on 31/08/16.
 */

public class BundleSerializationTest {

    private BundleSerialization bundleSerialization;

    //@Mock
    //JSONArray jsonArray;

    @Before
    public void setup() throws Exception {
        //MockitoAnnotations.initMocks(this);

        bundleSerialization = new BundleSerialization();
    }

    @Test
    public void testGetters() throws Exception {

        String empty = "empty";

        bundleSerialization.putString(empty, null);
        bundleSerialization.putFloat(empty, null);
        bundleSerialization.putInt(empty, null);

        bundleSerialization.putBool(empty, null);
        bundleSerialization.putBundle(empty, null);
        bundleSerialization.putDate(empty, null);
        bundleSerialization.putUrl(empty, null);

        bundleSerialization.getBundle();
        bundleSerialization.setBundle(null);
    }
}
