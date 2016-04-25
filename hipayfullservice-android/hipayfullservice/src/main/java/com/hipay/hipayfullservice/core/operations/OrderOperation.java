package com.hipay.hipayfullservice.core.operations;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.utils.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

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