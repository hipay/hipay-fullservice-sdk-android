package com.hipay.hipayfullservice.core.client;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import com.hipay.hipayfullservice.core.client.operations.OrderOperation;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.network.HttpResponse;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by nfillion on 18/02/16.
 */
public class GatewayClient implements LoaderManager.LoaderCallbacks<HttpResponse> {

    private Context context;

    public GatewayClient(Context ctx) {

        context = ctx;

        String params = this.testRequests();
        //String params = this.orderTestRequests();

        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryParams", params);

        Activity activity = (Activity)context;
        activity.getLoaderManager().initLoader(0, queryBundle, this);

    }

    private String orderTestRequests() {

        //OrderRequest orderRequest = new OrderRequest();

        //orderRequest.setAmount(225);
        //orderRequest.setCurrency("EUR");

        //StringBuilder stringBuilder = new StringBuilder("TEST_SDK_IOS_").append(Calendar.getInstance().getTimeInMillis()/1000);

        //orderRequest.setOrderId(stringBuilder.toString());
        //orderRequest.setShortDescription("Outstanding item");
        //orderRequest.getCustomer().setCountry("FR");
        //orderRequest.getCustomer().setFirstname("John");
        //orderRequest.getCustomer().setLastname("Doe");

        //orderRequest.setPaymentCardGroupingEnabled(true);
        //orderRequest.setMultiUse(true);
        //orderRequest.setPaymentProductCategoryList(

        //Arrays.asList(

        //PaymentProduct.PaymentProductCodeCB,
        //PaymentProduct.PaymentProductCodeMasterCard,
        //PaymentProduct.PaymentProductCodeVisa,
        //PaymentProduct.PaymentProductCodeAmericanExpress,
        //PaymentProduct.PaymentProductCodeMaestro,
        //PaymentProduct.PaymentProductCodeDiners
        //)
        //);

        //orderRequest.getCustomer().setEmail("nfillion@hipay.com");

        //orderRequest.setAuthenticationIndicator(CardTokenPaymentMethodRequest.AuthenticationIndicator.AuthenticationIndicatorUndefined);

        //return orderRequest.getStringParameters();

        return null;
    }

    private String testRequests() {

        PaymentPageRequest paymentPageRequest = new PaymentPageRequest();

        paymentPageRequest.setAmount(225);
        paymentPageRequest.setCurrency("EUR");

        StringBuilder stringBuilder = new StringBuilder("TEST_SDK_IOS_").append(Calendar.getInstance().getTimeInMillis()/1000);

        paymentPageRequest.setOrderId(stringBuilder.toString());
        paymentPageRequest.setShortDescription("Outstanding item");
        paymentPageRequest.getCustomer().setCountry("FR");
        paymentPageRequest.getCustomer().setFirstname("John");
        paymentPageRequest.getCustomer().setLastname("Doe");
        paymentPageRequest.setPaymentCardGroupingEnabled(true);
        paymentPageRequest.setMultiUse(true);
        paymentPageRequest.setPaymentProductCategoryList(

                Arrays.asList(

                        PaymentProduct.PaymentProductCodeCB,
                        PaymentProduct.PaymentProductCodeMasterCard,
                        PaymentProduct.PaymentProductCodeVisa,
                        PaymentProduct.PaymentProductCodeAmericanExpress,
                        PaymentProduct.PaymentProductCodeMaestro,
                        PaymentProduct.PaymentProductCodeDiners
                )
        );

        paymentPageRequest.getCustomer().setEmail("nfillion@hipay.com");

        paymentPageRequest.setAuthenticationIndicator(CardTokenPaymentMethodRequest.AuthenticationIndicator.AuthenticationIndicatorUndefined);

        OrderRequest orderRequest = new OrderRequest(paymentPageRequest);

        //TODO
        orderRequest.setPaymentProductCode("bcmc-mobile");

        return orderRequest.getStringParameters();
    }

    @Override
    public Loader<HttpResponse> onCreateLoader(int id, Bundle bundle) {
        //return new PaymentPageOperation(this, bundle);

        return new OrderOperation(context, bundle);
    }

    @Override
    public void onLoadFinished(Loader<HttpResponse> loader, HttpResponse data) {

        //TODO Result<HttpResponse>
        //TODO Result<Order>

        Log.i("data", "data : " + data);

        InputStream stream = data.getBodyStream();
        try {
            String response = data.readStream(stream);
            Log.i(response, response);

            try {
                JSONObject object = new JSONObject(response);

                Transaction transaction = Transaction.fromJSONObject(object);
                Log.i(transaction.toString(), transaction.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<HttpResponse> loader) {
    }
}
