package com.hipay.hipayfullservice.core.client;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hipay.hipayfullservice.core.client.interfaces.IReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.OrderReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.PaymentPageReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.PaymentProductsReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.SecureVaultReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.TransactionReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.TransactionsReqHandler;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.AbstractRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.PaymentPageRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.PaymentProductsCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.TransactionDetailsCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.TransactionsDetailsCallback;
import com.hipay.hipayfullservice.core.logging.Logger;
import com.hipay.hipayfullservice.core.network.AbstractHttpClient;
import com.hipay.hipayfullservice.core.network.HttpResult;
import com.hipay.hipayfullservice.core.operations.AbstractOperation;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.securevault.SecureVaultRequest;

import java.lang.ref.WeakReference;

/**
 * Created by nfillion on 21/01/16.
 */
public abstract class AbstractClient<T> implements LoaderManager.LoaderCallbacks<HttpResult>{

    private IReqHandler reqHandler;

    protected WeakReference<Context> contextWeakReference = null;
    private AbstractOperation operation;

    public AbstractClient(Context ctx) {

        contextWeakReference = new WeakReference<>(ctx);
    }

    protected void createRequest(T request, AbstractRequestCallback callback) {

        if (this.initReqHandler(request, callback)) {
            this.launchOperation();

        } else {
            throw new IllegalArgumentException();
        }
    }

    protected void createRequest(T request, String signature, AbstractRequestCallback callback) {

        if (this.initReqHandler(request, signature, callback)) {

            this.launchOperation();

        } else {
            throw new IllegalArgumentException();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void cancelOperation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            //it cancels the loading
            AbstractOperation operation = this.getOperation();
            if (operation != null) {
                operation.cancelLoad();
            }
        }

        //...and it actually brutally destroys the loader
        if (this.getContext() != null) {
            AppCompatActivity activity = (AppCompatActivity)this.getContext();
            activity.getSupportLoaderManager().destroyLoader(this.getLoaderId());
        }
    }

    protected void launchOperation() {

        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryParams", this.getQueryParams());
        queryBundle.putString("HS_signature", this.getSignature());

        AppCompatActivity activity = (AppCompatActivity)this.getContext();
        //activity.getSupportLoaderManager().enableDebugLogging(true);
        if (activity != null) {
            activity.getSupportLoaderManager().initLoader(this.getLoaderId(), queryBundle, this);
        }
    }

    public void reLaunchOperations(int loaderId) {

        AppCompatActivity activity = (AppCompatActivity)this.getContext();
        if (activity != null) {

            LoaderManager supportLoaderManager = activity.getSupportLoaderManager();
            if (supportLoaderManager.getLoader(loaderId) != null) {
                this.launchOperation();
            }
        }
    }


    private boolean initReqHandler(T request, String signature, AbstractRequestCallback callback) {

        if (signature != null && !signature.isEmpty()) {

            if (request instanceof PaymentPageRequest
                    && callback instanceof PaymentPageRequestCallback) {

                PaymentPageRequest paymentPageRequest = (PaymentPageRequest)request;
                PaymentPageRequestCallback paymentPageRequestCallback = (PaymentPageRequestCallback)callback;

                this.setReqHandler(new PaymentPageReqHandler(paymentPageRequest, signature, paymentPageRequestCallback));

                return true;

            } else if (request instanceof OrderRequest
                    && callback instanceof OrderRequestCallback) {

                OrderRequest orderRequest = (OrderRequest) request;
                OrderRequestCallback orderRequestCallback = (OrderRequestCallback)callback;

                this.setReqHandler(new OrderReqHandler(orderRequest, signature, orderRequestCallback));

                return true;

            }  else if (request instanceof String
                    && callback instanceof TransactionsDetailsCallback) {

                String orderId = (String) request;
                TransactionsDetailsCallback transactionsDetailsCallback = (TransactionsDetailsCallback)callback;

                this.setReqHandler(new TransactionsReqHandler(orderId, signature, transactionsDetailsCallback));

                return true;

            } else if (request instanceof String
                    && callback instanceof TransactionDetailsCallback) {

                String transactionReference = (String) request;
                TransactionDetailsCallback transactionDetailsCallback = (TransactionDetailsCallback)callback;

                this.setReqHandler(new TransactionReqHandler(transactionReference, signature, transactionDetailsCallback));

                return true;
            }
        }

        return false;
    }
    private boolean initReqHandler(T request, AbstractRequestCallback callback) {

        if (request instanceof SecureVaultRequest
                && callback instanceof SecureVaultRequestCallback) {

            SecureVaultRequest secureVaultRequest = (SecureVaultRequest) request;
            SecureVaultRequestCallback secureVaultRequestCallback = (SecureVaultRequestCallback)callback;

            this.setReqHandler(new SecureVaultReqHandler(secureVaultRequest, secureVaultRequestCallback));

            return true;

        } else if (request instanceof PaymentPageRequest
                && callback instanceof PaymentProductsCallback) {

            PaymentPageRequest paymentPageRequest = (PaymentPageRequest) request;
            PaymentProductsCallback paymentProductsCallback = (PaymentProductsCallback) callback;

            this.setReqHandler(new PaymentProductsReqHandler(paymentPageRequest, paymentProductsCallback));

            return true;
        }
        return false;
    }

    @Override
    public Loader<HttpResult> onCreateLoader(int id, Bundle bundle) {

        if (this.getContext() != null) {

            this.setOperation(this.getOperation(this.getContext(), bundle));

            //performs http request
            AbstractOperation operation = this.getOperation();

            String httpMethod = operation.getRequestType().getStringValue();
            String path = operation.concatUrl();

            Logger.d(new StringBuilder("<HTTP:> Performs ").append(httpMethod).append(" ").append(path).toString());

            return operation;
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<HttpResult> loader, HttpResult data) {

        if (this.getContext() != null) {
            this.getReqHandler().handleCallback(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<HttpResult> loader) {

        Log.i("reset", "reset");
        Log.i("reset", "reset");
    }

    protected String getQueryParams() {

        return this.getReqHandler().getReqQueryString();
    }

    protected String getSignature() {

        return this.getReqHandler().getReqSignatureString();
    }

    protected AbstractOperation getOperation(Context context, Bundle bundle) {

        return this.getReqHandler().getReqOperation(context, bundle);
    }

    protected int getLoaderId() {

        return this.getReqHandler().getLoaderId();
    }

    public enum RequestLoaderId {

        GenerateTokenReqLoaderId(0),
        OrderReqLoaderId(1),
        PaymentPageReqLoaderId(2),
        TransactionReqLoaderId(3),
        TransactionsReqLoaderId(4),
        PaymentProductsReqLoaderId(5);

        protected final Integer loaderId;

        RequestLoaderId(Integer loaderId) {
            this.loaderId = loaderId;
        }

        public Integer getIntegerValue() {
            return this.loaderId;
        }

        public static RequestLoaderId fromIntegerValue(Integer value) {

            if (value == null) return null;

            if (value.equals(GenerateTokenReqLoaderId.getIntegerValue())) {
                return GenerateTokenReqLoaderId;
            }

            if (value.equals(OrderReqLoaderId.getIntegerValue())) {
                return OrderReqLoaderId;
            }

            if (value.equals(PaymentPageReqLoaderId.getIntegerValue())) {
                return PaymentPageReqLoaderId;
            }

            if (value.equals(TransactionReqLoaderId.getIntegerValue())) {
                return TransactionReqLoaderId;
            }

            if (value.equals(TransactionsReqLoaderId.getIntegerValue())) {
                return TransactionsReqLoaderId;
            }

            if (value.equals(PaymentProductsReqLoaderId.getIntegerValue())) {
                return PaymentProductsReqLoaderId;
            }
            return null;
        }
    }



    protected Context getContext() {
        return contextWeakReference.get();
    }

    private IReqHandler getReqHandler() {
        return reqHandler;
    }

    private void setReqHandler(IReqHandler reqHandler) {
        this.reqHandler = reqHandler;
    }

    public AbstractOperation getOperation() {
        return operation;
    }

    public void setOperation(AbstractOperation operation) {
        this.operation = operation;
    }
}
