package com.hipay.hipayfullservice.core.client.interfaces.callbacks;

import com.hipay.hipayfullservice.core.models.PaymentCardToken;

/**
 * Created by nfillion on 09/03/16.
 */

public abstract class SecureVaultRequestCallback extends AbstractRequestCallback {
    public abstract void onSuccess(PaymentCardToken paymentCardToken);
}
