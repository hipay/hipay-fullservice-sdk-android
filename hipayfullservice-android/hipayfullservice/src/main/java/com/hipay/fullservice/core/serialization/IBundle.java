package com.hipay.fullservice.core.serialization;

import android.os.Bundle;

import java.net.URL;
import java.util.Date;
import java.util.Map;

/**
 * Created by nfillion on 18/03/16.
 */
public interface IBundle {

    void putFloat(String key, Float floatNumber);
    void putString(String key, String string);
    void putInt(String key, Integer integer);
    void putBool(String key, Boolean bool);
    void putUrl(String key, URL url);
    void putBundle(String key, Bundle bundle);
    void putDate(String key, Date date);
    void putMapJSON(String key, Map<String, String> map);
    Bundle getBundle();

}
