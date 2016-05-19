package com.hipay.hipayfullservice.core.client;

import android.content.Context;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.hipayfullservice.core.requests.securevault.SecureVaultRequest;

/**
 * Created by nfillion on 08/03/16.
 */
public class SecureVaultClient extends AbstractClient {

    public SecureVaultClient(Context context) {
        super(context);
    }

    public void createTokenRequest(String cardNumber,
                                      String cardExpiryMonth,
                                      String cardExpiryYear,
                                      String cardHolder,
                                      String cardCVV,
                                      boolean multiUse,
                                      SecureVaultRequestCallback callback

    ) {

        SecureVaultRequest secureVaultRequest = new SecureVaultRequest(
                cardNumber,
                cardExpiryMonth,
                cardExpiryYear,
                cardHolder,
                cardCVV,
                multiUse);

        super.createRequest(secureVaultRequest, callback);

    }
}
