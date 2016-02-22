package com.hipay.hipayfullservice.core.client;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.network.HttpResult;
import com.hipay.hipayfullservice.core.operations.GatewayOperation;

/**
 * Created by nfillion on 18/02/16.
 */
public abstract class GatewayClient<T1, T2> extends AbstractClient<T1, T2> implements LoaderManager.LoaderCallbacks<HttpResult> {

    public GatewayClient(Context context) {
        super(context);
    }

    public void createOrderRequest(T1 request, T2 callback) {

        super.createOrderRequest(request, callback);

        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryParams", this.getQueryParams());

        Activity activity = (Activity)this.getContext();
        activity.getLoaderManager().initLoader(0, queryBundle, this);
    }

    @Override
    public Loader<HttpResult> onCreateLoader(int id, Bundle bundle) {

        GatewayOperation gatewayOperation = this.getOperation(this.getContext(), bundle);
        return gatewayOperation;
    }

    @Override
    public void onLoadFinished(Loader<HttpResult> loader, HttpResult data) {

        this.handleCallbackResult(data);
    }

    @Override
    public void onLoaderReset(Loader<HttpResult> loader) {
    }

}
