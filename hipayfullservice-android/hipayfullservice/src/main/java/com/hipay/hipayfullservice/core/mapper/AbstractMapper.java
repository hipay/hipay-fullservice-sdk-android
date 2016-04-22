package com.hipay.hipayfullservice.core.mapper;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.IBehaviour;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Date;

/**
 * Created by nfillion on 25/01/16.
 */
public abstract class AbstractMapper<T> {

    protected T rawData;
    protected IBehaviour behaviour;

    protected abstract Object mappedObject();
    protected abstract Object mappedObjectFromBundle();
    protected abstract boolean isValid();

    public AbstractMapper(T rawData) {

        try {
            this.isMapperValid(rawData);

        } catch (InvalidParameterException exception) {
            //TODO

        } finally {
            //continue
        }
    }

    public AbstractMapper() {

    }

    private void isMapperValid(T rawData) {

        this.setRawData(rawData);

        if (this.rawData == null) {
            throw new InvalidParameterException();

        } else {

            if (this.rawData instanceof JSONObject) {

                JSONObject map = (JSONObject)this.getRawData();
                this.setBehaviour(new MapMapper(map));

            } else if (this.rawData instanceof JSONArray) {

                //TODO not using JSONArray for now

                //JSONArray list = (JSONArray)this.getRawData();
                //this.setBehaviour(new ListMapper(list));

            } else if (this.rawData instanceof Bundle) {

                Bundle bundle = (Bundle)this.getRawData();
                this.setBehaviour(new BundleMapper(bundle));
            }
        }

        if (!this.isValid()) {

            throw new InvalidParameterException();
        }
    }

    protected String getStringForKey(String key) {

        return this.getBehaviour().getStringForKey(key);
    }

    protected Float getFloatForKey(String key) {

        return this.getBehaviour().getFloatForKey(key);
    }
    protected Integer getIntegerForKey(String key) {

        return this.getBehaviour().getIntegerForKey(key);
    }

    protected String getLowercaseStringForKey(String key) {

        return this.getBehaviour().getLowercaseStringForKey(key);
    }

    protected URL getURLForKey(String key) {

        return this.getBehaviour().getURLForKey(key);
    }

    protected Boolean getBoolForKey(String key) {

        return this.getBehaviour().getBoolForKey(key);
    }

    protected Number getNumberForKey(String key) {

        return this.getBehaviour().getNumberForKey(key);
    }

    protected String getEnumCharForKey(String key) {

        return this.getBehaviour().getEnumCharForKey(key);
    }

    protected Date getDateForKey(String key) {

        return this.getBehaviour().getDateForKey(key);
    }

    protected Bundle getBundleForKey(String key) {

        return this.getBehaviour().getBundleForKey(key);
    }

    public T getRawData() {
        return rawData;
    }

    public void setRawData(T rawData) {
        this.rawData = rawData;
    }

    public IBehaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(IBehaviour behaviour) {
        this.behaviour = behaviour;
    }

}
