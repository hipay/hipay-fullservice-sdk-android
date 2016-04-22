package com.hipay.hipayfullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.hipayfullservice.core.errors.exceptions.HttpException;
import com.hipay.hipayfullservice.core.models.PaymentCardToken;
import com.hipay.hipayfullservice.core.operations.SecureVaultOperation;
import com.hipay.hipayfullservice.core.requests.securevault.SecureVaultRequest;

import org.json.JSONObject;

/**
 * Created by nfillion on 09/03/16.
 */

public class SecureVaultReqHandler extends AbstractReqHandler {

    public SecureVaultReqHandler(SecureVaultRequest secureVaultRequest, SecureVaultRequestCallback callback) {

        this.setRequest(secureVaultRequest);
        this.setCallback(callback);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

        //TODO on traite le JSON
        PaymentCardToken paymentCardToken = PaymentCardToken.fromJSONObject(jsonObject);

        SecureVaultRequestCallback orderRequestCallback = (SecureVaultRequestCallback)this.getCallback();
        orderRequestCallback.onSuccess(paymentCardToken);
    }

    @Override
    public void onError(Exception apiException) {

        SecureVaultRequestCallback orderRequestCallback = (SecureVaultRequestCallback)this.getCallback();
        orderRequestCallback.onError(apiException);

    }

    @Override
    public String getReqQueryString() {

        SecureVaultRequest secureVaultRequest = (SecureVaultRequest)this.getRequest();
        return secureVaultRequest.getStringParameters();
    }

    @Override
    public SecureVaultOperation getReqOperation(Context context, Bundle bundle) {
        return new SecureVaultOperation(context, bundle);
    }

    @Override
    public int getLoaderId() {
        return 1;
    }

}
