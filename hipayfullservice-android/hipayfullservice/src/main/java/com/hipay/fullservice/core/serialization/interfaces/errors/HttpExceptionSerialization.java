package com.hipay.fullservice.core.serialization.interfaces.errors;

import com.hipay.fullservice.core.serialization.interfaces.AbstractSerialization;

/**
 * Created by nfillion on 26/03/2018.
 */
public class HttpExceptionSerialization extends AbstractSerialization {

    public HttpExceptionSerialization(Exception exception) {
        super(exception);
    }

}
