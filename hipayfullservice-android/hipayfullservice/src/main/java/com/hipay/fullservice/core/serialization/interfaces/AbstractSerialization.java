package com.hipay.fullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.fullservice.core.serialization.BundleSerialization;
import com.hipay.fullservice.core.serialization.IBundle;

import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public abstract class AbstractSerialization<T> implements ISerialization {

    protected T model;

    protected IBundle bundle;

    public AbstractSerialization(T model) {
        this.bundle = new BundleSerialization();
        this.setModel(model);
    }

    public Map<String, String> getSerializedRequest() {
        return null;
    }

    ;

    public Bundle getSerializedBundle() {
        return null;
    }

    ;

    public String getQueryString() {
        return null;
    }

    ;

    protected T getModel() {
        return model;
    }

    private void setModel(T model) {
        this.model = model;
    }

    public Bundle getBundle() {
        return this.bundle.getBundle();
    }

}