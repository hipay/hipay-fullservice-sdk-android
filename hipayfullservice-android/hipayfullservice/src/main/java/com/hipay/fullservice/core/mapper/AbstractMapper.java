package com.hipay.fullservice.core.mapper;

import android.net.Uri;
import android.os.Bundle;

import com.hipay.fullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.fullservice.core.mapper.interfaces.IBehaviour;
import com.hipay.fullservice.core.mapper.interfaces.IMapper;
import com.hipay.fullservice.core.mapper.interfaces.MapMapper;
import com.hipay.fullservice.core.mapper.interfaces.UriMapper;

import org.json.JSONObject;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.Map;

/**
 * Created by nfillion on 25/01/16.
 */
public abstract class AbstractMapper<T> implements IMapper {

    private IBehaviour behaviour;

    public AbstractMapper(T rawData) {
        try {
            this.isMapperValid(rawData);
        } catch (InvalidParameterException exception) {
            // json/bundle is not valid
        }
    }

    public AbstractMapper() {
    }

    private void isMapperValid(T rawData) {
        if (rawData == null) {
            throw new InvalidParameterException();
        } else {
            if (rawData instanceof JSONObject) {
                JSONObject map = (JSONObject) rawData;
                this.setBehaviour(new MapMapper(map));
            } else if (rawData instanceof Bundle) {
                Bundle bundle = (Bundle) rawData;
                this.setBehaviour(new BundleMapper(bundle));
            } else if (rawData instanceof Uri) {
                Uri uri = (Uri) rawData;
                this.setBehaviour(new UriMapper(uri));
            }
        }

        if (!this.isValid()) {
            throw new InvalidParameterException();
        }
    }

    protected String getStringForKey(String key) {
        return this.behaviour.getStringForKey(key);
    }

    protected Float getFloatForKey(String key) {
        return this.getBehaviour().getFloatForKey(key);
    }

    protected Integer getIntegerForKey(String key) {
        return this.behaviour.getIntegerForKey(key);
    }

    protected String getLowercaseStringForKey(String key) {
        return this.behaviour.getLowercaseStringForKey(key);
    }

    protected URL getURLForKey(String key) {
        return this.getBehaviour().getURLForKey(key);
    }

    protected Boolean getBoolForKey(String key) {
        return this.getBehaviour().getBoolForKey(key);
    }

    protected String getEnumCharForKey(String key) {
        return this.behaviour.getEnumCharForKey(key);
    }

    protected Date getDateForKey(String key) {
        return this.getBehaviour().getDateForKey(key);
    }

    protected JSONObject getJSONObjectForKey(String key) {
        return this.behaviour.getJSONObjectForKey(key);
    }

    protected Bundle getBundleForKey(String key) {
        return this.behaviour.getBundleForKey(key);
    }

    protected Map<String, String> getMapJSONForKey(String key) {
        return this.behaviour.getMapJSONForKey(key);
    }

    public IBehaviour getBehaviour() {
        return behaviour;
    }

    private void setBehaviour(IBehaviour behaviour) {
        this.behaviour = behaviour;
    }

}
