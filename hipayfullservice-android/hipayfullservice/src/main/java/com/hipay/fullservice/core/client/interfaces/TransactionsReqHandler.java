package com.hipay.fullservice.core.client.interfaces;

/**
 * Created by nfillion on 08/05/16.
 */

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.TransactionsDetailsCallback;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.operations.GatewayOperation;
import com.hipay.fullservice.core.operations.TransactionsDetailsOperation;
import com.hipay.fullservice.core.utils.DataExtractor;
import com.hipay.fullservice.core.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TransactionsReqHandler extends AbstractReqHandler {

    public TransactionsReqHandler(String orderId, String signature, TransactionsDetailsCallback callback) {
        super(orderId, signature, callback);
    }

    @Override
    protected String getDomain() {
        return "<Gateway>";
    }

    @Override
    public String getReqQueryString() {

        String orderId = (String)this.getRequest();

        Map<String, String> orderIdMap = new HashMap<>();
        orderIdMap.put("orderid", orderId);

        return Utils.queryStringFromMap(orderIdMap);
    }

    @Override
    public GatewayOperation getReqOperation(Context context, Bundle bundle) {
        return new TransactionsDetailsOperation(context, bundle);
    }

    @Override
    public int getLoaderId() {

        return AbstractClient.RequestLoaderId.TransactionsReqLoaderId.getIntegerValue();
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

        Log.i(jsonObject.toString(), jsonObject.toString());

        JSONObject transactionJSONObject = DataExtractor.getJSONObjectFromField(jsonObject, "transaction");

        if (transactionJSONObject != null) {

            String state = DataExtractor.getStringFromField(transactionJSONObject, "state");
            if (state == null)  {

                List<Transaction> transactionList = new ArrayList<>();
                Transaction transaction;

                Iterator<String> iter = transactionJSONObject.keys();
                while (iter.hasNext()) {
                    String key = iter.next();

                    JSONObject object = DataExtractor.getJSONObjectFromField(transactionJSONObject,key);
                    transaction = Transaction.fromJSONObject(object);
                    transactionList.add(transaction);
                }

                Collections.sort(transactionList, new Comparator<Transaction>() {
                    @Override
                    public int compare(Transaction lhs, Transaction rhs) {

                        if (lhs.isHandled()) return -1;
                        if (rhs.isHandled()) return 1;
                        return rhs.getDateCreated().compareTo(lhs.getDateCreated());

                    }
                });

                TransactionsDetailsCallback transactionsDetailsCallback = (TransactionsDetailsCallback)this.getCallback();
                transactionsDetailsCallback.onSuccess(transactionList);

            } else {

                Transaction transaction = Transaction.fromJSONObject(transactionJSONObject);

                TransactionsDetailsCallback transactionDetailsCallback = (TransactionsDetailsCallback)this.getCallback();
                transactionDetailsCallback.onSuccess(new ArrayList<>(Collections.singletonList(transaction)));

            }
        }
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
    }

    @Override
    public void onError(Exception apiException) {
        super.onError(apiException);

        Log.i(apiException.toString(), apiException.toString());
        TransactionsDetailsCallback transactionsDetailsCallback = (TransactionsDetailsCallback)this.getCallback();
        transactionsDetailsCallback.onError(apiException);
    }
}
