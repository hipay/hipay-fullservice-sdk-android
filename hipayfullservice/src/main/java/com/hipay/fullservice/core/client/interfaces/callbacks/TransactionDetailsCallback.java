package com.hipay.fullservice.core.client.interfaces.callbacks;

import com.hipay.fullservice.core.models.Transaction;

/**
 * Created by HiPay on 23/02/16.
 */
public abstract class TransactionDetailsCallback extends AbstractRequestCallback {
    public abstract void onSuccess(Transaction transaction);
}
