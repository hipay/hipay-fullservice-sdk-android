package com.hipay.hipayfullservice.core.client.interfaces.callbacks;

import com.hipay.hipayfullservice.core.models.Transaction;

/**
 * Created by nfillion on 23/02/16.
 */
public abstract class TransactionDetailsCallback extends AbstractRequestCallback {
    public abstract void onSuccess(Transaction transaction);
}
