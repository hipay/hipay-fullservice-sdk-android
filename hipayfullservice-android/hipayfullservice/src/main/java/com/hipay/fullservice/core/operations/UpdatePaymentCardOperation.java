package com.hipay.fullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by nfillion on 08/03/16.
 */

public class UpdatePaymentCardOperation extends SecureVaultOperation {

    public UpdatePaymentCardOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @Override
    public HttpMethod getRequestType() {
        return HttpMethod.POST;
    }

    @Override
    protected String getSignature() {
        return null;
    }

    @Override
    public String concatUrl() {
        return "token/update";
    }

    @Override
    protected boolean isV2() {
        return false;
    }
}