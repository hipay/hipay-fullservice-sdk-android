package com.hipay.fullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.utils.enums.HttpMethod;

/**
 * Created by nfillion on 08/03/16.
 */

public class LookupPaymentCardOperation extends SecureVaultOperation {

    public LookupPaymentCardOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @Override
    public HttpMethod getRequestType() {
        return HttpMethod.GET;
    }

    @Override
    protected String getSignature() {
        return null;
    }

    @Override
    public String concatUrl() {
        return "token";
    }

    @Override
    protected boolean isV2() {
        return false;
    }
}

