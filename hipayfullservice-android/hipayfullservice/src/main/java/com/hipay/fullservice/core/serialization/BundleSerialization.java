package com.hipay.fullservice.core.serialization;

import android.os.Bundle;

import com.hipay.fullservice.core.utils.Utils;

import org.json.JSONObject;

import java.net.URL;
import java.util.Date;
import java.util.Map;

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
    public void putMapJSON(String key, Map<String,String> map) {

        if (map != null) {

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(map);

            } catch (Exception exception) {
                jsonObject = null;

            } finally {

                if (jsonObject != null) {

                    String mapString = jsonObject.toString();
                    this.getBundle().putString(key, mapString);
                }
            }
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
