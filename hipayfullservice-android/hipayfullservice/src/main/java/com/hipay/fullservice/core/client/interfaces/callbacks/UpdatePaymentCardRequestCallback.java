package com.hipay.fullservice.core.client.interfaces.callbacks;

import com.hipay.fullservice.core.models.PaymentCardToken;

/**
 * Created by nfillion on 09/03/16.
 */

public abstract class UpdatePaymentCardRequestCallback extends AbstractRequestCallback {
    public abstract void onSuccess(PaymentCardToken paymentCardToken);
}

