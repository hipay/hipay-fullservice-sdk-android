package com.hipay.hipayfullservice.core.client;

import android.content.Context;

import com.hipay.hipayfullservice.core.client.interfaces.callbacks.PaymentPageRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.PaymentProductsCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.TransactionDetailsCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.TransactionsDetailsCallback;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;

/**
 * Created by nfillion on 18/02/16.
 */
public class GatewayClient extends AbstractClient  {

    public static final String SIGNATURE_TAG = "signature_tag";

    public GatewayClient(Context context) {
        super(context);
    }

    public void requestNewOrder(OrderRequest orderRequest, String signature, OrderRequestCallback orderRequestCallback) {
        super.createRequest(orderRequest, signature, orderRequestCallback);
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
