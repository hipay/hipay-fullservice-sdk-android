package com.hipay.hipayfullservice.core.serialization;
/**
 * Created by nfillion on 08/09/16.
 */

import com.hipay.hipayfullservice.core.models.FraudScreening;
import com.hipay.hipayfullservice.core.serialization.interfaces.FraudScreeningSerialization;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static junit.framework.Assert.assertNull;

/**
 * Created by nfillion on 01/09/16.
 */

public class FraudScreeningSerializationTest {

    @Before
    public void setup() throws Exception {
    }

    @Mock
    FraudScreening fraudScreening;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testMethods() throws Exception {

        FraudScreeningSerialization fraudScreeningSerialization = new FraudScreeningSerialization(fraudScreening);
        assertNull(fraudScreeningSerialization.getQueryString());
        assertNull(fraudScreeningSerialization.getSerializedRequest());
    }
}

