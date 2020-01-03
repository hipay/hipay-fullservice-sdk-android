package com.hipay.fullservice.core.serialization.interfaces;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by HiPay on 01/09/16.
 */
public class AbstractSerializationTest {

    //@Mock
    //AbstractSerialization abstractSerialization;

    //@Rule
    //public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() throws Exception {
    }

    @Test
    public void testGetters() throws Exception {

        //when(abstractSerialization.getQueryString()).thenReturn("hello");
        //assertNull(DataExtractor.getStringFromField(jsonObject, notFound));
        //assertEquals(abstractSerialization.getQueryString(), "hello");

        //when(abstractSerialization.getSerializedRequest()).thenReturn(new HashMap());
        //assertNull(DataExtractor.getStringFromField(jsonObject, notFound));
        //assertEquals(abstractSerialization.getSerializedRequest(), new HashMap());

        /*
        AbstractSerialization abstractSerialization = new AbstractSerialization(null) {
            @Override
            public Map<String, String> getSerializedRequest() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }
        };
        */


        /*

        public abstract Map<String, String> getSerializedRequest();

        public Bundle getSerializedBundle() {

            this.setBundleBehaviour(new BundleSerialization());
            return this.getBundle();
        }

        public abstract String getQueryString();
        */

        //abstractSerialization.getQueryString();

    }
}
