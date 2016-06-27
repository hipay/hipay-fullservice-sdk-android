package com.hipay.hipayfullservice.core.operations;

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
    protected HttpMethod getRequestType() {
        return HttpMethod.POST;
    }

    @Override
    protected String concatUrl() {
        return "order";
    }
}