package com.hipay.hipayfullservice.core.serialization.interfaces;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.models.AbstractModel;
import com.hipay.hipayfullservice.core.requests.AbstractRequest;
import com.hipay.hipayfullservice.core.serialization.IBundle;

import java.net.URL;
import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public abstract class AbstractSerialization implements ISerialization {

    protected AbstractRequest request;
    protected AbstractModel model;
    IBundle bundleBehaviour;

    public abstract Map<String, String> getSerializedRequest();
    public abstract Bundle getSerializedBundle();
    public abstract String getQueryString();

    //TODO it should be Bundle or Map behaviour
    public IBundle getBundleBehaviour() {
        return bundleBehaviour;
    }

    public void setBundleBehaviour(IBundle bundleBehaviour) {
        this.bundleBehaviour = bundleBehaviour;
    }

    protected AbstractRequest getRequest() {
        return request;
    }

    protected void setRequest(AbstractRequest request) {
        this.request = request;
    }

    public AbstractModel getModel() {
        return model;
    }

    public void setModel(AbstractModel model) {
        this.model = model;
    }

    protected void putFloatForKey(String key, Float floatNumber) {
        this.getBundleBehaviour().putFloat(key, floatNumber);
    }

    protected void putStringForKey(String key, String string) {
        this.getBundleBehaviour().putString(key, string);
    }

    protected void putUrlForKey(String key, URL url) {
        this.getBundleBehaviour().putUrl(key, url);
    }

    protected void putIntForKey(String key, Integer integer) {
        this.getBundleBehaviour().putInt(key, integer);
    }

    protected void putBoolForKey(String key, Boolean bool) {
        this.getBundleBehaviour().putBool(key, bool);
    }

    protected Bundle getBundle() {
       return this.getBundleBehaviour().getBundle();
    }
}