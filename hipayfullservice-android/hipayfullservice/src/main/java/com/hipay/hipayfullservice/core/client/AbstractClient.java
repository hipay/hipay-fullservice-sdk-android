package com.hipay.hipayfullservice.core.client;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.interfaces.IReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.OrderReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.network.HttpResult;
import com.hipay.hipayfullservice.core.operations.GatewayOperation;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;

/**
 * Created by nfillion on 21/01/16.
 */
public abstract class AbstractClient<T1, T2> {

    private T1 request;
    private T2 callback;

    private IReqHandler reqHandler;

    //TODO set it weakreference
    private Context context;

    public AbstractClient(Context ctx) {
        context = ctx;
    }

    public void createRequest(T1 request, T2 callback) {

        this.initReqHandler(request, callback);
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
        }
    }

    protected void handleCallbackResult(HttpResult result) {

        this.getReqHandler().handleCallback(result);
    }

    protected String getQueryParams() {

        return this.getReqHandler().getReqQueryString();
    }

    protected GatewayOperation getOperation(Context context, Bundle bundle) {

        return this.getReqHandler().getReqOperation(context, bundle);
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
}
