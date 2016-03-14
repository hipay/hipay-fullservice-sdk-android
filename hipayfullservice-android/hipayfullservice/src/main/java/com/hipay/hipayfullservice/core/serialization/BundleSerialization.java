package com.hipay.hipayfullservice.core.serialization;

import android.os.Bundle;

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

        } else {
            //TODO don't put anything if float is null
            //this.getBundle().putFloat(key, -1.f);
        }
    }

    @Override
    public void putString(String key, String string) {

        this.getBundle().putString(key, string);
    }

    @Override
    public void putInt(String key, Integer integer) {

        if (integer != null) {

            this.getBundle().putInt(key, integer);

        } else {

            //TODO don't put anything if integer is null
            //this.getBundle().putInt(key, -1);
        }
    }

    @Override
    public void putBool(String key, Boolean bool) {

        if (bool != null) {
            this.getBundle().putBoolean(key, bool);

        } else {

            //TODO don't put anything if bool is null
            //this.getBundle().putBoolean(key, false);
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
