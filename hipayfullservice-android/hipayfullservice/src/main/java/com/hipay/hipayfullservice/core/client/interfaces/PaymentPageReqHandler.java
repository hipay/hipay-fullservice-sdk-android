package com.hipay.hipayfullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.PaymentPageRequestCallback;
import com.hipay.hipayfullservice.core.models.HostedPaymentPage;
import com.hipay.hipayfullservice.core.operations.GatewayOperation;
import com.hipay.hipayfullservice.core.operations.PaymentPageOperation;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;

import org.json.JSONObject;

/**
 * Created by nfillion on 12/04/16.
 */

public class PaymentPageReqHandler extends AbstractReqHandler {

    public PaymentPageReqHandler(PaymentPageRequest paymentPageRequest, PaymentPageRequestCallback callback) {

        this.setRequest(paymentPageRequest);
        this.setCallback(callback);
    }

    @Override
    public String getReqQueryString() {

        PaymentPageRequest paymentPageRequest = (PaymentPageRequest)this.getRequest();
        return paymentPageRequest.getStringParameters();
    }

    @Override
    public GatewayOperation getReqOperation(Context context, Bundle bundle) {
        return new PaymentPageOperation(context, bundle);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

        HostedPaymentPage hostedPaymentPage = HostedPaymentPage.fromJSONObject(jsonObject);

        PaymentPageRequestCallback orderRequestCallback = (PaymentPageRequestCallback)this.getCallback();
        orderRequestCallback.onSuccess(hostedPaymentPage);
    }

    @Override
    public void onError(Exception apiException) {

        PaymentPageRequestCallback orderRequestCallback = (PaymentPageRequestCallback)this.getCallback();
        orderRequestCallback.onError(apiException);
    }

    @Override
    public int getLoaderId() {
        return 2;
    }
}
