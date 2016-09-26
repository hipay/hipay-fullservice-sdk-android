package com.hipay.fullservice.core.client.interfaces.callbacks;

import com.hipay.fullservice.core.models.HostedPaymentPage;

/**
 * Created by nfillion on 12/04/16.
 */
public abstract class PaymentPageRequestCallback extends AbstractRequestCallback {
    public abstract void onSuccess(HostedPaymentPage transaction);
}
