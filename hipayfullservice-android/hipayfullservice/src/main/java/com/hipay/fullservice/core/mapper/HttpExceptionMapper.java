package com.hipay.fullservice.core.mapper;

import android.os.Bundle;

import com.hipay.fullservice.core.errors.exceptions.HttpException;
import com.hipay.fullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.fullservice.core.mapper.interfaces.MapMapper;

/**
 * Created by nfillion on 22/03/2018.
 */

public class HttpExceptionMapper extends AbstractMapper {

    public HttpExceptionMapper(Object rawData) {
        super(rawData);
    }

    @Override
    public boolean isValid() {
        return this.getBehaviour() instanceof MapMapper || this.getBehaviour() instanceof BundleMapper;
    }

    @Override
    public HttpException mappedObject() {
        Bundle exceptionBundle = this.getBundleForKey("cause");
        HttpException httpException = null;
        if (exceptionBundle != null) {
            httpException = HttpException.fromBundle(exceptionBundle);
        }

        HttpException object = new HttpException(
                this.getStringForKey("message"),
                this.getIntegerForKey("code"),
                httpException
        );

        return object;
    }
}