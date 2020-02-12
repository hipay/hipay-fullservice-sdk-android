package com.hipay.fullservice.core.client.interfaces.callbacks;

import com.hipay.fullservice.core.models.PaymentCardToken;

/**
 * Created by HiPay on 09/03/16.
 */

public abstract class LookupPaymentCardRequestCallback extends AbstractRequestCallback {
    public abstract void onSuccess(PaymentCardToken paymentCardToken);
}

