package com.hipay.hipayfullservice.core.client.interfaces.callbacks;

import com.hipay.hipayfullservice.core.models.Transaction;

import java.util.List;

/**
 * Created by nfillion on 23/02/16.
 */
public abstract class TransactionsDetailsCallback extends AbstractRequestCallback {
    public abstract void onSuccess(List<Transaction> transactions);
}
