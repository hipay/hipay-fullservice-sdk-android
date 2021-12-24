package com.hipay.fullservice.core.serialization;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.hipay.fullservice.core.models.AbstractModel;
import com.hipay.fullservice.core.requests.AbstractRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

/**
 * Created by HiPay on 31/08/16.
 */

public class AbstractSerializationMapperTest {

    //private AbstractSerializationMapper abstractSerializationMapper;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    AbstractSerializationMapper abstractSerializationMapper;

    @Test
    public void testGetters() throws Exception {

        //AbstractSerializationMapper mapper = Mockito.mock(AbstractSerializationMapper.class);
        when(abstractSerializationMapper.getQueryString()).thenReturn("hello");
        assertEquals(abstractSerializationMapper.getQueryString(), "hello");

        when(abstractSerializationMapper.getSerializedObject()).thenReturn(new HashMap<String, String>());
        assertEquals(abstractSerializationMapper.getSerializedObject(), new HashMap<String, String>());


        abstractSerializationMapper = new AbstractSerializationMapper(new Exception()) {
            @Override
            public String toString() {
                return super.toString();
            }
        };

        abstractSerializationMapper = new AbstractSerializationMapper(

                new AbstractModel() {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                }
        ) {
            @Override
            public String toString() {
                return super.toString();
            }
        };

        abstractSerializationMapper = new AbstractSerializationMapper(

                new AbstractRequest() {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                }
        ) {

            @Override
            public String toString() {
                return super.toString();
            }

        };
        abstractSerializationMapper.getSerialization();
        abstractSerializationMapper.setSerialization(null);


    }
}
