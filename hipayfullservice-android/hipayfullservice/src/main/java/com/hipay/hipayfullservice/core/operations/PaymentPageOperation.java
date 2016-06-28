package com.hipay.hipayfullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by nfillion on 22/01/16.
 */

public class PaymentPageOperation extends GatewayOperation {

    public PaymentPageOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @Override
    protected String concatUrl() {
        return "hpayment";
    }

    @Override
    protected boolean isV2() {
        return false;
    }

    @Override
    protected HttpMethod getRequestType() {
        return HttpMethod.POST;
    }
}
