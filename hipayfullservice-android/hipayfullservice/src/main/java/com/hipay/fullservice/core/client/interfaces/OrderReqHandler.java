package com.hipay.fullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.operations.GatewayOperation;
import com.hipay.fullservice.core.operations.OrderOperation;
import com.hipay.fullservice.core.requests.order.OrderRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nfillion on 22/02/16.
 */
public class OrderReqHandler extends AbstractReqHandler {

    public OrderReqHandler(OrderRequest orderRequest, String signature, OrderRequestCallback callback) {
        super(orderRequest, signature, callback);
    }

    @Override
    protected String getDomain() {
        return "<Gateway>";
    }

    @Override
    public String getReqQueryString() {

        OrderRequest orderRequest = (OrderRequest)this.getRequest();
        return orderRequest.getStringParameters();
    }

    @Override
    public GatewayOperation getReqOperation(Context context, Bundle bundle) {
        return new OrderOperation(context, bundle);
    }

    @Override
    public int getLoaderId() {
        return AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue();
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

        Transaction transaction = Transaction.fromJSONObject(jsonObject);

        OrderRequestCallback orderRequestCallback = (OrderRequestCallback)this.getCallback();
        orderRequestCallback.onSuccess(transaction);
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {

    }

    @Override
    public void onError(Exception apiException) {
        super.onError(apiException);

        OrderRequestCallback orderRequestCallback = (OrderRequestCallback)this.getCallback();
        orderRequestCallback.onError(apiException);
    }
}
