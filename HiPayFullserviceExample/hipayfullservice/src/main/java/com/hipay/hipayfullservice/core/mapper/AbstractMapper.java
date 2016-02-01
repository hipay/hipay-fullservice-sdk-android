package com.hipay.hipayfullservice.core.mapper;

import com.hipay.hipayfullservice.core.mapper.interfaces.IBehaviour;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapBehaviour;

import org.json.JSONObject;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

/**
 * Created by nfillion on 25/01/16.
 */
public abstract class AbstractMapper {

    protected Object rawData;
    protected IBehaviour behaviour;

    protected abstract Object mappedObject();
    protected abstract boolean isClassValid();

    public AbstractMapper(Object rawData) {

        try {
            this.isMapperValid(rawData);

        } catch (InvalidParameterException exception) {
            //TODO handle invalid parameters

        } finally {
            //continue
        }
    }

    public AbstractMapper() {

    }

    private void isMapperValid(Object rawData) {

        this.setRawData(rawData);

        if (this.rawData == null) {
            throw new InvalidParameterException();

        } else {

            if (this.rawData instanceof JSONObject) {

                JSONObject map = (JSONObject)this.getRawData();
                this.setBehaviour(new MapBehaviour(map));

            } else if (this.rawData instanceof List) {

                //JSONArray list = (JSONArray)this.getRawData();
                //this.setBehaviour(new ListBehaviour(list));
            }
        }

        if (!this.isClassValid()) {

            throw new InvalidParameterException();
        }
    }

    //TODO build custom getters

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

    public Object getRawData() {
        return rawData;
    }

    public void setRawData(Object rawData) {
        this.rawData = rawData;
    }

    public IBehaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(IBehaviour behaviour) {
        this.behaviour = behaviour;
    }

}
