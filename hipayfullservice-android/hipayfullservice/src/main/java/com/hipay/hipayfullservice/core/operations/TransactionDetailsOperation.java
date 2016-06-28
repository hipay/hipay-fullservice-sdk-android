package com.hipay.hipayfullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by nfillion on 08/05/16.
 */

public class TransactionDetailsOperation  extends GatewayOperation {

    public TransactionDetailsOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @Override
    protected String concatUrl() {
        return "transaction";
    }

    @Override
    protected boolean isV2() {
        return false;
    }

    @Override
    protected HttpMethod getRequestType() {
        return HttpMethod.GET;
    }
}
