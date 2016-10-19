package com.hipay.fullservice.core.client.interfaces;

/**
 * Created by nfillion on 28/06/16.
 */

import android.content.Context;
import android.os.Bundle;

import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.PaymentProductsCallback;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.operations.GatewayOperation;
import com.hipay.fullservice.core.operations.PaymentProductsOperation;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.utils.DataExtractor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentProductsReqHandler extends AbstractReqHandler {

    public PaymentProductsReqHandler(PaymentPageRequest request, PaymentProductsCallback callback) {
        super(request, callback);
    }

    @Override
    protected String getDomain() {
        return "<Gateway>";
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
        return AbstractClient.RequestLoaderId.PaymentProductsReqLoaderId.getIntegerValue();
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
        super.onError(apiException);

        PaymentProductsCallback paymentProductsCallback = (PaymentProductsCallback)this.getCallback();
        paymentProductsCallback.onError(apiException);

    }
}
