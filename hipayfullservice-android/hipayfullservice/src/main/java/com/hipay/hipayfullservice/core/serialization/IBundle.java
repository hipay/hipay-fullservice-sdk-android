package com.hipay.hipayfullservice.core.serialization;

import android.os.Bundle;

/**
 * Created by nfillion on 18/03/16.
 */
public interface IBundle {

    void putFloat(String key, Float floatNumber);
    void putString(String key, String string);
    void putInt(String key, Integer integer);
    void putBool(String key, Boolean bool);
    Bundle getBundle();

}
