package com.hipay.fullservice.core.client;

import android.content.Context;
import android.os.AsyncTask;

import com.hipay.fullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.PaymentPageRequestCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.PaymentProductsCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.TransactionDetailsCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.TransactionsDetailsCallback;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.monitoring.CheckoutData;
import com.hipay.fullservice.core.monitoring.CheckoutDataNetwork;
import com.hipay.fullservice.core.monitoring.Monitoring;
import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;

import java.util.Date;

/**
 * Created by HiPay on 18/02/16.
 */
public class GatewayClient extends AbstractClient  {

    public static final String SIGNATURE_TAG = "signature_tag";

    public GatewayClient(Context context) {
        super(context);
    }

    public void requestNewOrder(final OrderRequest orderRequest, String signature, final OrderRequestCallback orderRequestCallback) {

        CheckoutData checkoutData = new CheckoutData();
        checkoutData.setEvent(CheckoutData.Event.request);
        checkoutData.setAmount(orderRequest.getAmount());
        checkoutData.setCurrency(orderRequest.getCurrency());
        checkoutData.setOrderID(orderRequest.getOrderId());
        checkoutData.setPaymentMethod(orderRequest.getPaymentProductCode());

        Monitoring monitoring = new Monitoring();
        monitoring.setRequestDate(new Date());
        checkoutData.setMonitoring(monitoring);

        if (CheckoutData.checkoutData != null) {
            checkoutData.setIdentifier(CheckoutData.checkoutData.getIdentifier());
            checkoutData.setCardCountry(CheckoutData.checkoutData.getCardCountry());
        }

        CheckoutData.checkoutData = checkoutData;


        super.createRequest(orderRequest, signature, new OrderRequestCallback() {
            @Override
            public void onSuccess(Transaction transaction) {


                CheckoutData.checkoutData.setTransactionID(transaction.getTransactionReference());
                CheckoutData.checkoutData.setStatus(String.valueOf(transaction.getStatus().getIntegerValue()));
                CheckoutData.checkoutData.getMonitoring().setResponseDate(new Date());

                new CheckoutDataNetwork().execute(CheckoutData.checkoutData);

                orderRequestCallback.onSuccess(transaction);
            }

            @Override
            public void onError(Exception error) {
                orderRequestCallback.onError(error);
            }
        });
    }

    public void createHostedPaymentPageRequest(PaymentPageRequest hostedPaymentPage, String signature, PaymentPageRequestCallback paymentPageRequestCallback) {
        super.createRequest(hostedPaymentPage, signature, paymentPageRequestCallback);
    }

    public void getTransactionWithReference(String reference, String signature, TransactionDetailsCallback transactionDetailsCallback) {
        super.createRequest(reference, signature, transactionDetailsCallback);
    }

    public void getTransactionsWithOrderId(String orderId, String signature, TransactionsDetailsCallback transactionsDetailsCallback) {
        super.createRequest(orderId, signature, transactionsDetailsCallback);
    }

    public void getPaymentProducts(PaymentPageRequest paymentPageRequest, PaymentProductsCallback paymentProductsCallback) {
        super.createRequest(paymentPageRequest, paymentProductsCallback);
    }
}
