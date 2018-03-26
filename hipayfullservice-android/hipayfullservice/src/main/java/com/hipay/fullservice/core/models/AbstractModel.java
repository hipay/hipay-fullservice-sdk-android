package com.hipay.fullservice.core.models;

import android.os.Bundle;

import com.hipay.fullservice.core.serialization.SerializationFactory;
import com.hipay.fullservice.core.serialization.interfaces.ISerialization;

import java.io.Serializable;

/**
 * Created by nfillion on 22/03/16.
 */
public abstract class AbstractModel implements Serializable {

    public Bundle toBundle() {
        ISerialization mapper = SerializationFactory.newInstance(this);
        return mapper.getSerializedBundle();
    }
}
