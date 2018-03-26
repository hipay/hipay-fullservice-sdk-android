package com.hipay.fullservice.core.requests;

import android.os.Bundle;

import com.hipay.fullservice.core.serialization.SerializationFactory;
import com.hipay.fullservice.core.serialization.interfaces.ISerialization;

import java.util.Map;

/**
 * Created by nfillion on 04/02/16.
 */
public abstract class AbstractRequest {

    public String getStringParameters() {
        ISerialization mapper = SerializationFactory.newInstance(this);
        return mapper.getQueryString();
    }

    public Map<String, String> getSerializedObject() {
        ISerialization mapper = SerializationFactory.newInstance(this);
        return mapper.getSerializedRequest();
    }

    public Bundle toBundle() {
        ISerialization mapper = SerializationFactory.newInstance(this);
        return mapper.getSerializedBundle();
    }
}
