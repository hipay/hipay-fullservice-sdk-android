package com.hipay.hipayfullservice.core.client.interfaces;

/**
 * Created by nfillion on 08/05/16.
 */

import android.content.Context;
import android.os.Bundle;

import com.hipay.hipayfullservice.core.client.AbstractClient;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.TransactionDetailsCallback;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.operations.GatewayOperation;
import com.hipay.hipayfullservice.core.operations.TransactionDetailsOperation;
import com.hipay.hipayfullservice.core.utils.DataExtractor;

import org.json.JSONArray;
import org.json.JSONObject;

public class TransactionReqHandler extends AbstractReqHandler {

    public TransactionReqHandler(String transactionReference, String signature, TransactionDetailsCallback callback) {
        super(transactionReference, signature, callback);
    }

    @Override
    protected String getDomain() {
        return "<Gateway>";
    }

    @Override
    public String getReqQueryString() {

        String transactionReference = (String)this.getRequest();

        //just return the transactionReference
        return transactionReference;
    }

    @Override
    public GatewayOperation getReqOperation(Context context, Bundle bundle) {
        return new TransactionDetailsOperation(context, bundle);
    }

    @Override
    public int getLoaderId() {

        return AbstractClient.RequestLoaderId.TransactionReqLoaderId.getIntegerValue();
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

        JSONObject transactionJSONObject = DataExtractor.getJSONObjectFromField(jsonObject, "transaction");

        Transaction transaction = Transaction.fromJSONObject(transactionJSONObject);

        TransactionDetailsCallback transactionDetailsCallback = (TransactionDetailsCallback)this.getCallback();
        transactionDetailsCallback.onSuccess(transaction);
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
    }

    @Override
    public void onError(Exception apiException) {
        super.onError(apiException);

        TransactionDetailsCallback transactionDetailsCallback = (TransactionDetailsCallback)this.getCallback();
        transactionDetailsCallback.onError(apiException);

    }
}
