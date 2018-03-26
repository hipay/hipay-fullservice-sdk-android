package com.hipay.fullservice.core.serialization.interfaces.errors;

import android.os.Bundle;

import com.hipay.fullservice.core.errors.exceptions.ApiException;

/**
 * Created by nfillion on 26/03/2018.
 */
public class ApiExceptionSerialization extends AbstractExceptionSerialization {

    public ApiExceptionSerialization(Exception exception) {
        super(exception);
    }

    @Override
    public Bundle getSerializedBundle() {
        super.getSerializedBundle();
        ApiException apiException = (ApiException) this.getModel();
        this.bundle.putInt("apiCode", apiException.getApiCode());
        return this.getBundle();
    }
}
