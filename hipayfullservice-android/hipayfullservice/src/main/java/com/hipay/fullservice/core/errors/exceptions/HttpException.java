package com.hipay.fullservice.core.errors.exceptions;


import android.os.Bundle;

import com.hipay.fullservice.core.mapper.HttpExceptionMapper;
import com.hipay.fullservice.core.serialization.SerializationFactory;
import com.hipay.fullservice.core.serialization.interfaces.ISerialization;

/**
 * Created by nfillion on 05/04/16.
 */
public class HttpException extends AbstractException {

    public HttpException(String message, Integer statusCode, Throwable throwable) {
        super(message, statusCode, throwable);
    }

    public HttpException(Exception exception) {
        super(exception.getLocalizedMessage());
    }

    public static HttpException fromBundle(Bundle bundle) {
        HttpExceptionMapper mapper = new HttpExceptionMapper(bundle);
        return mapper.mappedObject();
    }

    public Bundle toBundle() {
        ISerialization mapper = SerializationFactory.newInstance(this);
        return mapper.getSerializedBundle();
    }


}
