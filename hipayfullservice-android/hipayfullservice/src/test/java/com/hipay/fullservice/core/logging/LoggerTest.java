package com.hipay.fullservice.core.logging;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;

/**
 * Created by nfillion on 02/09/16.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class LoggerTest {

    @Before
    public void setup() throws Exception {

        PowerMockito.mockStatic(Log.class);
    }

    @Test
    public void testLogger() throws Exception {

        String helloWorld = "Hello world!";

        BDDMockito.given(Log.d(Logger.TAG,helloWorld)).willReturn(Log.DEBUG);
        BDDMockito.given(Log.v(Logger.TAG,helloWorld)).willReturn(Log.VERBOSE);
        BDDMockito.given(Log.i(Logger.TAG,helloWorld)).willReturn(Log.INFO);
        BDDMockito.given(Log.e(Logger.TAG,helloWorld)).willReturn(Log.ERROR);
        BDDMockito.given(Log.w(Logger.TAG,helloWorld)).willReturn(Log.WARN);

        assertEquals(Logger.d(helloWorld), Log.DEBUG);
        assertEquals(Logger.v(helloWorld), Log.VERBOSE);
        assertEquals(Logger.i(helloWorld), Log.INFO);
        assertEquals(Logger.e(helloWorld), Log.ERROR);
        assertEquals(Logger.w(helloWorld), Log.WARN);

    }
}
