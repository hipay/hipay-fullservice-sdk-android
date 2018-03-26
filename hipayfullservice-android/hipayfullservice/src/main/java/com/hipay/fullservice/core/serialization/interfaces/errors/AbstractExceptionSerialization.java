package com.hipay.fullservice.core.serialization.interfaces.errors;

import android.os.Bundle;

import com.hipay.fullservice.core.errors.exceptions.AbstractException;
import com.hipay.fullservice.core.errors.exceptions.HttpException;
import com.hipay.fullservice.core.serialization.interfaces.AbstractSerialization;

/**
 * Created by nfillion on 26/03/2018.
 */

public class AbstractExceptionSerialization extends AbstractSerialization {

    public AbstractExceptionSerialization(Exception exception) {
        super(exception);
    }

    @Override
    public Bundle getSerializedBundle() {
        AbstractException apiException = (AbstractException) this.getModel();

        this.bundle.putString("message", apiException.getMessage());
        this.bundle.putInt("code", apiException.getStatusCode());

        Throwable exception = apiException.getCause();
        if (exception != null) {

            HttpException httpSubException = (HttpException) exception;
            Bundle bundle = httpSubException.toBundle();
            this.bundle.putBundle("cause", bundle);
        }

        return this.getBundle();
    }
}
