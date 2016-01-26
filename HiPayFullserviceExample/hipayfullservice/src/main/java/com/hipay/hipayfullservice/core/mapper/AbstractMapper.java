package com.hipay.hipayfullservice.core.mapper;


/**
 * Created by nfillion on 25/01/16.
 */
public abstract class AbstractMapper {

    protected Object rawData;

    public AbstractMapper(Object rawData) {
        this.rawData = rawData;
    }

    protected abstract boolean isClassValid();
    protected abstract boolean isValid();
    protected abstract Object mappedObject();

}
