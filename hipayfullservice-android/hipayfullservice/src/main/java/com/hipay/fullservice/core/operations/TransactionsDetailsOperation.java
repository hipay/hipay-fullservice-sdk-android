package com.hipay.fullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.utils.enums.HttpMethod;

/**
 * Created by nfillion on 08/05/16.
 */

public class TransactionsDetailsOperation  extends GatewayOperation {

    public TransactionsDetailsOperation(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @Override
    public String concatUrl() {
        return "transaction";
    }

    @Override
    protected boolean isV2() {
        return false;
    }

    @Override
    public HttpMethod getRequestType() {
        return HttpMethod.GET;
    }
}
