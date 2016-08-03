package com.hipay.hipayfullservice.core.client.interfaces;

/**
 * Created by nfillion on 28/06/16.
 */

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.PaymentProductsCallback;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.operations.GatewayOperation;
import com.hipay.hipayfullservice.core.operations.PaymentProductsOperation;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.utils.DataExtractor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentProductsReqHandler extends AbstractReqHandler {

    public PaymentProductsReqHandler(PaymentPageRequest request, PaymentProductsCallback callback) {
        super(request, callback);
    }

    @Override
    public String getReqQueryString() {

        PaymentPageRequest paymentPageRequest = (PaymentPageRequest)this.getRequest();
        return paymentPageRequest.getStringParameters();
    }

    @Override
    public GatewayOperation getReqOperation(Context context, Bundle bundle) {
        return new PaymentProductsOperation(context, bundle);
    }

    @Override
    public int getLoaderId() {
        return 5;
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

        //JSONObject transactionJSONObject = DataExtractor.getJSONObjectFromField(jsonObject, "transaction");

        //Transaction transaction = Transaction.fromJSONObject(transactionJSONObject);

        // work on jsonObject list.

        //paymentProductsCallback.onSuccess();
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {

        List<PaymentProduct> paymentProducts = new ArrayList<>();

        PaymentProduct paymentProduct;
        for(int i=0; i < jsonArray.length() ; i++) {

            JSONObject jsonObject = DataExtractor.getJSONObjectFromField(jsonArray, i);
            paymentProduct = PaymentProduct.fromJSONObject(jsonObject);

            paymentProducts.add(paymentProduct);
        }

        PaymentProductsCallback paymentProductsCallback = (PaymentProductsCallback)this.getCallback();
        paymentProductsCallback.onSuccess(paymentProducts);
    }

    @Override
    public void onError(Exception apiException) {

        PaymentProductsCallback paymentProductsCallback = (PaymentProductsCallback)this.getCallback();
        paymentProductsCallback.onError(apiException);

    }
}
