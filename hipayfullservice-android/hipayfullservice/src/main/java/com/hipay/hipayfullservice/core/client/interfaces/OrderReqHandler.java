package com.hipay.hipayfullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.network.HttpResult;
import com.hipay.hipayfullservice.core.operations.GatewayOperation;
import com.hipay.hipayfullservice.core.operations.OrderOperation;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nfillion on 22/02/16.
 */
public class OrderReqHandler extends AbstractReqHandler {

    public OrderReqHandler(OrderRequest orderRequest, OrderRequestCallback callback) {

        this.setRequest(orderRequest);
        this.setCallback(callback);
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
    public void handleCallback(HttpResult data) {

        InputStream stream = data.getBodyStream();

        try {
            String response = data.readStream(stream);

            try {
                JSONObject object = new JSONObject(response);

                Transaction transaction = Transaction.fromJSONObject(object);

                OrderRequestCallback orderRequestCallback = (OrderRequestCallback)this.getCallback();
                orderRequestCallback.onSuccess(transaction);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getLoaderId() {
        return 0;
    }
}
