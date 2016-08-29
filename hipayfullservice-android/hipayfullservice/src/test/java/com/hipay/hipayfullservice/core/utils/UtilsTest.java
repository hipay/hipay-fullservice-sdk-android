package com.hipay.hipayfullservice.core.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfillion on 30/08/16.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)

public class UtilsTest {

    @Before
    public void setup() throws Exception {
        //MockitoAnnotations.initMocks(this);

        PowerMockito.mockStatic(TextUtils.class);
    }

    @Test
    @TargetApi(Build.VERSION_CODES.KITKAT)

    public void testUtils() throws Exception {

        /*
        URL baseUrl = new URL("https://www.hipay.com");
        String path1 = "path1";
        String path2 = "path2";

        URL url1 = Utils.concatenatePath(baseUrl, path1);
        URL url2 = Utils.concatenatePath(url1, path2);

        assertEquals(url2, new URL("https://www.hipay.com/path1/path2"));

        URL url3 = Utils.concatenateParams(baseUrl, path1+"="+path2);
        assertEquals(url3, new URL("https://www.hipay.com?path1=path2"));

        Map<String, String> helloWorldMap = new HashMap<>();
        helloWorldMap.put("hello", "world");
        helloWorldMap.put("hi", "there");

        List<String> parameters = new ArrayList<>(2);
        parameters.add("hi=there");
        parameters.add("hello=world");

        String result = "hello=world&hi=there";
        BDDMockito.given(TextUtils.join("&", parameters)).willReturn(result);

        String queryStringFromMap = Utils.queryStringFromMap(helloWorldMap);
        assertEquals(queryStringFromMap, result);

        String helloWorld = "Hello world!";
        InputStream stream;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
            stream =new ByteArrayInputStream(helloWorld.getBytes(StandardCharsets.UTF_8));

        } else {
            stream = new ByteArrayInputStream(helloWorld.getBytes("UTF-8"));
        }

        assertEquals(Utils.readStream(stream), helloWorld);
        */
    }
}
