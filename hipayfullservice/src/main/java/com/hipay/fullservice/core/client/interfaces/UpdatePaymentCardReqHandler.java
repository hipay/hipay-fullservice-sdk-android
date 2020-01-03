package com.hipay.fullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.UpdatePaymentCardRequestCallback;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.operations.UpdatePaymentCardOperation;
import com.hipay.fullservice.core.requests.securevault.UpdatePaymentCardRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by HiPay on 09/03/16.
 */

public class UpdatePaymentCardReqHandler extends AbstractReqHandler {

    public UpdatePaymentCardReqHandler(UpdatePaymentCardRequest updatePaymentCardRequest, UpdatePaymentCardRequestCallback callback) {
        super(updatePaymentCardRequest, callback);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

        PaymentCardToken paymentCardToken = PaymentCardToken.fromJSONObject(jsonObject);

        UpdatePaymentCardRequestCallback updatePaymentCardRequestCallback = (UpdatePaymentCardRequestCallback) this.getCallback();
        updatePaymentCardRequestCallback.onSuccess(paymentCardToken);
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
    }

    @Override
    public void onError(Exception apiException) {
        super.onError(apiException);

        UpdatePaymentCardRequestCallback updatePaymentCardRequestCallback = (UpdatePaymentCardRequestCallback) this.getCallback();
        updatePaymentCardRequestCallback.onError(apiException);
    }

    @Override
    protected String getDomain() {
        return "<SecureVault>";
    }

    @Override
    public String getReqQueryString() {

        UpdatePaymentCardRequest updatePaymentCardRequest = (UpdatePaymentCardRequest) this.getRequest();
        return updatePaymentCardRequest.getStringParameters();
    }

    @Override
    public UpdatePaymentCardOperation getReqOperation(Context context, Bundle bundle) {
        return new UpdatePaymentCardOperation(context, bundle);
    }

    @Override
    public int getLoaderId() {
        return AbstractClient.RequestLoaderId.UpdatePaymentCardLoaderId.getIntegerValue();
    }

}

