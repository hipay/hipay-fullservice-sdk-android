package com.hipay.hipayfullservice.core.client;

import android.content.Context;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;

/**
 * Created by nfillion on 18/02/16.
 */
public class GatewayClient extends AbstractClient<OrderRequest, OrderRequestCallback>  {

    public GatewayClient(Context context) {
        super(context);
    }

    public void createOrderRequest(OrderRequest orderRequest, OrderRequestCallback orderRequestCallback) {

        super.createRequest(orderRequest, orderRequestCallback);
    }
}
