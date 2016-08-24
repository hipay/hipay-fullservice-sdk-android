package com.hipay.hipayfullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by nfillion on 28/06/16.
 */

public class PaymentProductsOperation extends GatewayOperation {

    public PaymentProductsOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @Override
    public String concatUrl() {
        return "available-payment-products";
    }

    @Override
    protected boolean isV2() {
        return true;
    }

    @Override
    public HttpMethod getRequestType() {
        return HttpMethod.GET;
    }

}
