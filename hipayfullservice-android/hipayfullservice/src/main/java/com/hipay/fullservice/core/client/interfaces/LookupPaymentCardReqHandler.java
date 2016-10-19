package com.hipay.fullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.LookupPaymentCardRequestCallback;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.operations.LookupPaymentCardOperation;
import com.hipay.fullservice.core.requests.securevault.LookupPaymentCardRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nfillion on 09/03/16.
 */

public class LookupPaymentCardReqHandler extends AbstractReqHandler {

    public LookupPaymentCardReqHandler(LookupPaymentCardRequest lookupPaymentCardRequest, LookupPaymentCardRequestCallback callback) {
        super(lookupPaymentCardRequest, callback);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

        PaymentCardToken paymentCardToken = PaymentCardToken.fromJSONObject(jsonObject);

        LookupPaymentCardRequestCallback lookupPaymentCardRequestCallback = (LookupPaymentCardRequestCallback) this.getCallback();
        lookupPaymentCardRequestCallback.onSuccess(paymentCardToken);
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
    }

    @Override
    public void onError(Exception apiException) {
        super.onError(apiException);

        LookupPaymentCardRequestCallback lookupPaymentCardRequestCallback = (LookupPaymentCardRequestCallback) this.getCallback();
        lookupPaymentCardRequestCallback.onError(apiException);
    }

    @Override
    protected String getDomain() {
        return "<SecureVault>";
    }

    @Override
    public String getReqQueryString() {

        LookupPaymentCardRequest lookupPaymentCardRequest = (LookupPaymentCardRequest) this.getRequest();
        return lookupPaymentCardRequest.getStringParameters();
    }

    @Override
    public LookupPaymentCardOperation getReqOperation(Context context, Bundle bundle) {
        return new LookupPaymentCardOperation(context, bundle);
    }

    @Override
    public int getLoaderId() {
        return AbstractClient.RequestLoaderId.UpdatePaymentCardLoaderId.getIntegerValue();
    }
}

