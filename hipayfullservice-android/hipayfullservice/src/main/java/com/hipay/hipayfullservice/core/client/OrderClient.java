package com.hipay.hipayfullservice.core.client;

import android.content.Context;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;

/**
 * Created by nfillion on 22/02/16.
 */
public class OrderClient extends GatewayClient<OrderRequest, OrderRequestCallback> {

    public OrderClient(Context context) {
        super(context);
    }

    public void createOrderRequest(OrderRequest request, OrderRequestCallback callback) {
        super.createOrderRequest(request, callback);
    }
}
