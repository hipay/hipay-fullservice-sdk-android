package com.hipay.fullservice.core.errors.exceptions;

import android.os.Bundle;

import com.hipay.fullservice.core.mapper.ApiExceptionMapper;
import com.hipay.fullservice.core.serialization.SerializationFactory;
import com.hipay.fullservice.core.serialization.interfaces.ISerialization;

/**
 * Created by nfillion on 05/04/16.
 */
public class ApiException extends AbstractException {

    private Integer apiCode;

    public ApiException(String message, Integer statusCode, Integer apiCode, Throwable throwable) {
        super(message, statusCode, throwable);
        this.apiCode = apiCode;
    }

    public static ApiException fromBundle(Bundle bundle) {
        ApiExceptionMapper mapper = new ApiExceptionMapper(bundle);
        return mapper.mappedObject();
    }

    public Bundle toBundle() {
        ISerialization mapper = SerializationFactory.newInstance(this);
        return mapper.getSerializedBundle();
    }

    public Integer getApiCode() {
        return apiCode;
    }
}
