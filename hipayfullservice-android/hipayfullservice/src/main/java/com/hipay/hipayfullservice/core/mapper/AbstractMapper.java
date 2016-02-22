package com.hipay.hipayfullservice.core.mapper;

import com.hipay.hipayfullservice.core.mapper.interfaces.IBehaviour;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapBehaviour;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

/**
 * Created by nfillion on 25/01/16.
 */
public abstract class AbstractMapper<T> {

    protected T rawData;
    protected IBehaviour behaviour;

    protected abstract Object mappedObject();
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
                this.setBehaviour(new MapBehaviour(map));

            } else if (this.rawData instanceof JSONArray) {

                //JSONArray list = (JSONArray)this.getRawData();
                //this.setBehaviour(new ListBehaviour(list));
            }
        }

        if (!this.isValid()) {

            throw new InvalidParameterException();
        }
    }

    protected String getStringForKey(String key) {

        return this.getBehaviour().getStringForKey(key);
    }

    protected Integer getIntegerForKey(String key) {

        return this.getBehaviour().getIntergerForKey(key);
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
