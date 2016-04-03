package com.hipay.hipayfullservice.core.client;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.hipay.hipayfullservice.core.client.interfaces.IReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.OrderReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.SecureVaultReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.hipayfullservice.core.network.HttpResult;
import com.hipay.hipayfullservice.core.operations.AbstractOperation;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.securevault.SecureVaultRequest;

/**
 * Created by nfillion on 21/01/16.
 */
public abstract class AbstractClient<T1, T2> implements LoaderManager.LoaderCallbacks<HttpResult>{

    private T1 request;
    private T2 callback;

    private IReqHandler reqHandler;

    //TODO set it weakreference
    private Context context;
    private AbstractOperation operation;

    public AbstractClient(Context ctx) {
        context = ctx;
    }

    protected void createRequest(T1 request, T2 callback) {

        this.initReqHandler(request, callback);
        this.launchOperation();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void cancelOperation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            AbstractOperation operation = this.getOperation();
            if (operation != null) {

                operation.cancelLoad();
            }
        }
    }

    protected void launchOperation() {

        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryParams", this.getQueryParams());

        Activity activity = (Activity)this.getContext();
        activity.getLoaderManager().initLoader(this.getLoaderId(), queryBundle, this);
    }

    private void initReqHandler(T1 request, T2 callback) {

        this.setRequest(request);
        this.setCallback(callback);

        if (this.getRequest() instanceof PaymentPageRequest) {

            //TODO paymentPage

        } else if (this.getRequest() instanceof OrderRequest
                && callback instanceof OrderRequestCallback) {

            OrderRequest orderRequest = (OrderRequest) this.getRequest();
            OrderRequestCallback orderRequestCallback = (OrderRequestCallback)this.getCallback();

            this.setReqHandler(new OrderReqHandler(orderRequest, orderRequestCallback));

        } else if (this.getRequest() instanceof SecureVaultRequest
                && callback instanceof SecureVaultRequestCallback) {

            SecureVaultRequest secureVaultRequest = (SecureVaultRequest) this.getRequest();
            SecureVaultRequestCallback secureVaultRequestCallback = (SecureVaultRequestCallback) this.getCallback();

            this.setReqHandler(new SecureVaultReqHandler(secureVaultRequest, secureVaultRequestCallback));

        }
    }

    @Override
    public Loader<HttpResult> onCreateLoader(int id, Bundle bundle) {

        this.setOperation(this.getOperation(this.getContext(), bundle));
        return this.getOperation();
    }

    @Override
    public void onLoadFinished(Loader<HttpResult> loader, HttpResult data) {

        this.getReqHandler().handleCallback(data);
    }

    @Override
    public void onLoaderReset(Loader<HttpResult> loader) {
    }

    protected String getQueryParams() {

        return this.getReqHandler().getReqQueryString();
    }

    protected AbstractOperation getOperation(Context context, Bundle bundle) {

        return this.getReqHandler().getReqOperation(context, bundle);
    }

    protected int getLoaderId() {

        return this.getReqHandler().getLoaderId();
    }


    protected T1 getRequest() {
        return request;
    }

    protected void setRequest(T1 request) {
        this.request = request;
    }

    protected Context getContext() {
        return context;
    }

    protected void setContext(Context context) {

        this.context = context;
    }

    private IReqHandler getReqHandler() {
        return reqHandler;
    }

    private void setReqHandler(IReqHandler reqHandler) {
        this.reqHandler = reqHandler;
    }

    protected T2 getCallback() {
        return callback;
    }

    protected void setCallback(T2 callback) {
        this.callback = callback;
    }

    public AbstractOperation getOperation() {
        return operation;
    }

    public void setOperation(AbstractOperation operation) {
        this.operation = operation;
    }
}
