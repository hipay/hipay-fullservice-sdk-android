package com.hipay.hipayfullservice.core.client.interfaces;

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.hipayfullservice.core.models.PaymentCardToken;
import com.hipay.hipayfullservice.core.network.HttpResult;
import com.hipay.hipayfullservice.core.operations.SecureVaultOperation;
import com.hipay.hipayfullservice.core.requests.securevault.SecureVaultRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nfillion on 09/03/16.
 */

public class SecureVaultReqHandler extends AbstractReqHandler {

    public SecureVaultReqHandler(SecureVaultRequest secureVaultRequest, SecureVaultRequestCallback callback) {

        this.setRequest(secureVaultRequest);
        this.setCallback(callback);
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
    public void handleCallback(HttpResult data) {

        InputStream stream = data.getBodyStream();

        try {
            String response = data.readStream(stream);

            try {
                JSONObject object = new JSONObject(response);

                PaymentCardToken paymentCardToken = PaymentCardToken.fromJSONObject(object);

                SecureVaultRequestCallback orderRequestCallback = (SecureVaultRequestCallback)this.getCallback();
                orderRequestCallback.onSuccess(paymentCardToken);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLoaderId() {
        return 1;
    }
}
