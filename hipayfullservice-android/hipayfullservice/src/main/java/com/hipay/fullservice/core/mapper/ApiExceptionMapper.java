package com.hipay.fullservice.core.mapper;

import android.os.Bundle;

import com.hipay.fullservice.core.errors.exceptions.ApiException;
import com.hipay.fullservice.core.errors.exceptions.HttpException;
import com.hipay.fullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.fullservice.core.mapper.interfaces.MapMapper;

/**
 * Created by nfillion on 22/03/2018.
 */

public class ApiExceptionMapper extends AbstractMapper {

    public ApiExceptionMapper(Object rawData) {
        super(rawData);
    }


    @Override
    public boolean isValid() {
        return this.getBehaviour() instanceof MapMapper || getBehaviour() instanceof BundleMapper;
    }

    @Override
    public ApiException mappedObject() {
        Bundle exceptionBundle = this.getBundleForKey("cause");
        HttpException httpException = null;
        if (exceptionBundle != null) {
            httpException = HttpException.fromBundle(exceptionBundle);
        }

        ApiException object = new ApiException(
                this.getStringForKey("message"),
                this.getIntegerForKey("code"),
                this.getIntegerForKey("apiCode"),
                httpException
        );

        return object;
    }
}

