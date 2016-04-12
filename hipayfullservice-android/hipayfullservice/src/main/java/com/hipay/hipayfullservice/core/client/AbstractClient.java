package com.hipay.hipayfullservice.core.client;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Build;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.interfaces.IReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.OrderReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.PaymentPageReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.SecureVaultReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.AbstractRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.PaymentPageRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.hipayfullservice.core.network.HttpResult;
import com.hipay.hipayfullservice.core.operations.AbstractOperation;
import com.hipay.hipayfullservice.core.requests.AbstractRequest;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.securevault.SecureVaultRequest;

import java.lang.ref.WeakReference;

/**
 * Created by nfillion on 21/01/16.
 */
public abstract class AbstractClient implements LoaderManager.LoaderCallbacks<HttpResult>{

    private IReqHandler reqHandler;

    //TODO set it weakreference
    protected WeakReference<Context> contextWeakReference = null;
    private AbstractOperation operation;

    public AbstractClient(Context ctx) {

        contextWeakReference = new WeakReference<>(ctx);
    }

    protected void createRequest(AbstractRequest request, AbstractRequestCallback callback) {

        if (this.initReqHandler(request, callback)) {
            this.launchOperation();

        } else {
            throw new IllegalArgumentException();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void cancelOperation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            //it cancels the loading
            AbstractOperation operation = this.getOperation();
            if (operation != null) {
                operation.cancelLoad();
            }
        }

        //...and it actually brutally destroys the loader
        if (this.getContext() != null) {
            Activity activity = (Activity)this.getContext();
            activity.getLoaderManager().destroyLoader(this.getLoaderId());
        }
    }

    protected void launchOperation() {

        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryParams", this.getQueryParams());

        Activity activity = (Activity)this.getContext();
        activity.getLoaderManager().initLoader(this.getLoaderId(), queryBundle, this);
    }

    private boolean initReqHandler(AbstractRequest request, AbstractRequestCallback callback) {

        if (request instanceof PaymentPageRequest
                && callback instanceof PaymentPageRequestCallback) {

            PaymentPageRequest paymentPageRequest = (PaymentPageRequest)request;
            PaymentPageRequestCallback paymentPageRequestCallback = (PaymentPageRequestCallback)callback;

            this.setReqHandler(new PaymentPageReqHandler(paymentPageRequest, paymentPageRequestCallback));

            return true;

        } else if (request instanceof OrderRequest
                && callback instanceof OrderRequestCallback) {

            OrderRequest orderRequest = (OrderRequest) request;
            OrderRequestCallback orderRequestCallback = (OrderRequestCallback)callback;

            this.setReqHandler(new OrderReqHandler(orderRequest, orderRequestCallback));

            return true;

        } else if (request instanceof SecureVaultRequest
                && callback instanceof SecureVaultRequestCallback) {

            SecureVaultRequest secureVaultRequest = (SecureVaultRequest) request;
            SecureVaultRequestCallback secureVaultRequestCallback = (SecureVaultRequestCallback)callback;

            this.setReqHandler(new SecureVaultReqHandler(secureVaultRequest, secureVaultRequestCallback));

            return true;
        }

        return false;
    }

    @Override
    public Loader<HttpResult> onCreateLoader(int id, Bundle bundle) {

        if (this.getContext() != null) {

            this.setOperation(this.getOperation(this.getContext(), bundle));
            return this.getOperation();
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<HttpResult> loader, HttpResult data) {

        if (this.getContext() != null) {
            this.getReqHandler().handleCallback(data);
        }
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

    protected Context getContext() {
        return contextWeakReference.get();
    }

    private IReqHandler getReqHandler() {
        return reqHandler;
    }

    private void setReqHandler(IReqHandler reqHandler) {
        this.reqHandler = reqHandler;
    }

    public AbstractOperation getOperation() {
        return operation;
    }

    public void setOperation(AbstractOperation operation) {
        this.operation = operation;
    }
}
