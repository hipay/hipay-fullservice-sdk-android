package com.hipay.fullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by HiPay on 08/03/16.
 */

public class GenerateTokenOperation extends SecureVaultOperation {

    public GenerateTokenOperation(Context context, Bundle bundle) {
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
        return "token/create";
    }

    @Override
    protected boolean isV2() {
        return false;
    }

}
