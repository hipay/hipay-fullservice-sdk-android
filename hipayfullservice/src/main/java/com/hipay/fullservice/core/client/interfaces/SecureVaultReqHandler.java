package com.hipay.fullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.operations.GenerateTokenOperation;
import com.hipay.fullservice.core.requests.securevault.GenerateTokenRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by HiPay on 09/03/16.
 */

public class SecureVaultReqHandler extends AbstractReqHandler {

    public SecureVaultReqHandler(GenerateTokenRequest generateTokenRequest, SecureVaultRequestCallback callback) {
        super(generateTokenRequest, callback);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

        PaymentCardToken paymentCardToken = PaymentCardToken.fromJSONObject(jsonObject);

        SecureVaultRequestCallback orderRequestCallback = (SecureVaultRequestCallback)this.getCallback();
        orderRequestCallback.onSuccess(paymentCardToken);
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
    }

    @Override
    public void onError(Exception apiException) {
        super.onError(apiException);

        SecureVaultRequestCallback orderRequestCallback = (SecureVaultRequestCallback)this.getCallback();
        orderRequestCallback.onError(apiException);
    }

    @Override
    protected String getDomain() {
        return "<SecureVault>";
    }

    @Override
    public String getReqQueryString() {

        GenerateTokenRequest generateTokenRequest = (GenerateTokenRequest)this.getRequest();
        return generateTokenRequest.getStringParameters();
    }

    @Override
    public GenerateTokenOperation getReqOperation(Context context, Bundle bundle) {
        return new GenerateTokenOperation(context, bundle);
    }

    @Override
    public int getLoaderId() {
        return AbstractClient.RequestLoaderId.GenerateTokenReqLoaderId.getIntegerValue();
    }

}
