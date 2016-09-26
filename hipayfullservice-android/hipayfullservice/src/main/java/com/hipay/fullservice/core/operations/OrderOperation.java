package com.hipay.fullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by nfillion on 16/02/16.
 */

public class OrderOperation extends GatewayOperation {

    public OrderOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @Override
    public HttpMethod getRequestType() {
        return HttpMethod.POST;
    }

    @Override
    public String concatUrl() {
        return "order";
    }

    @Override
    protected boolean isV2() {
        return false;
    }
}