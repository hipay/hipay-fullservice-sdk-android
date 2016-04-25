package com.hipay.hipayfullservice.core.errors.exceptions;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.mapper.AbstractMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;
import com.hipay.hipayfullservice.core.serialization.BundleSerialization;
import com.hipay.hipayfullservice.core.serialization.interfaces.AbstractSerialization;

import java.util.Map;

/**
 * Created by nfillion on 05/04/16.
 */
public class ApiException extends AbstractException {

    public ApiException(String message, Integer statusCode, Throwable throwable) {
        super(message, statusCode, throwable);
    }

    public static ApiException fromBundle(Bundle bundle) {

        ApiExceptionMapper mapper = new ApiExceptionMapper(bundle);
        return mapper.mappedObjectFromBundle();
    }

    public Bundle toBundle() {

        ApiException.ApiExceptionSerializationMapper mapper = new ApiException.ApiExceptionSerializationMapper(this);
        return mapper.getSerializedBundle();
    }

    protected static class ApiExceptionSerializationMapper extends AbstractSerializationMapper {

        protected ApiExceptionSerializationMapper(ApiException apiException) {
            super(apiException);
        }

        @Override
        protected String getQueryString() {
            return super.getQueryString();
        }

        @Override
        protected Bundle getSerializedBundle() {
            return super.getSerializedBundle();
        }
    }

    public static class ApiExceptionMapper extends AbstractMapper {

        public ApiExceptionMapper(Object rawData) {
            super(rawData);
        }

        @Override
        protected boolean isValid() {

            if (this.getBehaviour() instanceof MapMapper) {

                return true;

            } else if (getBehaviour() instanceof BundleMapper) {

                return true;
            }

            return true;
        }

        protected ApiException mappedObject() {

            return null;
        }

        @Override
        protected ApiException mappedObjectFromBundle() {

            Bundle exceptionBundle = this.getBundleForKey("cause");
            HttpException httpException = null;
            if (exceptionBundle != null) {
                httpException = HttpException.fromBundle(exceptionBundle);
            }

            ApiException object = new ApiException(
                    this.getStringForKey("message"),
                    this.getIntegerForKey("code"),
                    httpException
            );

            return object;
        }
    }

    public static class ApiExceptionSerialization extends AbstractSerialization {

        //TODO time to put a rawData instead of model/request in initializer
        public ApiExceptionSerialization(Exception exception) {
            this.setException(exception);
        }

        @Override
        public Map<String, String> getSerializedRequest() {
            return null;
        }

        @Override
        public Bundle getSerializedBundle() {

            this.setBundleBehaviour(new BundleSerialization());

            ApiException apiException = (ApiException)this.getException();

            this.putStringForKey("message", apiException.getMessage());
            this.putIntForKey("code", apiException.getStatusCode());

            Throwable exception = apiException.getCause();
            if (exception != null) {

                HttpException httpSubException = (HttpException) exception;
                Bundle bundle = httpSubException.toBundle();
                this.putBundleForKey("cause", bundle);
            }

            return this.getBundle();
        }

        @Override
        public String getQueryString() {
            return null;
        }
    }
}
