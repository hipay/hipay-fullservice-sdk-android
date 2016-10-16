package com.hipay.fullservice.core.serialization;

import android.os.Bundle;

import com.hipay.fullservice.core.utils.Utils;

import java.net.URL;
import java.util.Date;

/**
 * Created by nfillion on 18/03/16.
 */

public class BundleSerialization implements IBundle {

    protected Bundle bundle;

    public BundleSerialization() {

        this.setBundle(new Bundle());
    }

    @Override
    public void putFloat(String key, Float floatNumber) {

        if (floatNumber != null) {
            this.getBundle().putFloat(key, floatNumber);
        }
    }

    @Override
    public void putString(String key, String string) {

        if (string != null) {
            this.getBundle().putString(key, string);
        }
    }

    @Override
    public void putInt(String key, Integer integer) {

        if (integer != null) {

            this.getBundle().putInt(key, integer);

        } else {

            //this.getBundle().putInt(key, -1);
        }
    }

    @Override
    public void putBool(String key, Boolean bool) {

        if (bool != null) {
            this.getBundle().putBoolean(key, bool);
        }
    }

    @Override
    public void putBundle(String key, Bundle bundle) {

        if (bundle != null) {
            this.getBundle().putBundle(key, bundle);
        }
    }

    @Override
    public void putDate(String key, Date date) {

        if (date != null) {

            String stringDate = Utils.getStringFromDateISO8601(date);
            this.getBundle().putString(key, stringDate);
        }
    }

    @Override
    public void putUrl(String key, URL url) {

        if (url != null) {
            this.getBundle().putString(key, url.toString());
        }
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

}
