package com.hipay.hipayfullservice.core.errors.exceptions;

import android.os.Bundle;

import com.hipay.hipayfullservice.core.mapper.AbstractMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.BundleMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.serialization.AbstractSerializationMapper;
import com.hipay.hipayfullservice.core.serialization.interfaces.AbstractSerialization;

import java.util.Map;

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
        return mapper.mappedObjectFromBundle();
    }

    public Bundle toBundle() {

        HttpException.HttpExceptionSerializationMapper mapper = new HttpException.HttpExceptionSerializationMapper(this);
        return mapper.getSerializedBundle();
    }

    protected static class HttpExceptionSerializationMapper extends AbstractSerializationMapper {

        protected HttpExceptionSerializationMapper(HttpException httpException) {
            super(httpException);
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

    public static class HttpExceptionMapper extends AbstractMapper {

        public HttpExceptionMapper(Object rawData) {
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

        protected HttpException mappedObject() {

            return null;
        }

        @Override
        protected HttpException mappedObjectFromBundle() {

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

    public static class HttpExceptionSerialization extends AbstractSerialization {

        public HttpExceptionSerialization(Exception exception) {
            super(exception);
        }

        @Override
        public Map<String, String> getSerializedRequest() {
            return null;
        }

        @Override
        public Bundle getSerializedBundle() {

            super.getSerializedBundle();

            HttpException httpException = (HttpException)this.getModel();

            this.putStringForKey("message", httpException.getMessage());
            this.putIntForKey("code", httpException.getStatusCode());

            Throwable exception = httpException.getCause();
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
