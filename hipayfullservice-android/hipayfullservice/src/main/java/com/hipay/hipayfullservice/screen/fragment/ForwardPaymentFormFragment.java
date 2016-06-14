package com.hipay.hipayfullservice.screen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hipay.hipayfullservice.core.client.GatewayClient;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;

/**
 * Created by nfillion on 15/05/16.
 */

public class ForwardPaymentFormFragment extends AbstractPaymentFormFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCardInfoLayout.setVisibility(View.GONE);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mProgressBar.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER;

        if (getLoadingMode() == false) {
            setLoadingMode(true, false);
            launchRequest();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate", "onCreate");
    }

    @Override
    public void setLoadingMode(boolean loadingMode, boolean delay) {

        if (!delay) {

            if (loadingMode) {

                mProgressBar.setVisibility(View.VISIBLE);

            } else {

                mProgressBar.setVisibility(View.GONE);
            }
        }

        mLoadingMode = loadingMode;
    }

    @Override
    public void launchRequest() {

        Bundle args = getArguments();

        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
        final PaymentProduct paymentProduct = PaymentProduct.fromBundle(args.getBundle(PaymentProduct.TAG));

        OrderRequest orderRequest = new OrderRequest(paymentPageRequest);
        orderRequest.setPaymentProductCode(paymentProduct.getCode());

        mGatewayClient = new GatewayClient(getActivity());
        mCurrentLoading = 1;
        mGatewayClient.createOrderRequest(orderRequest, new OrderRequestCallback() {

            @Override
            public void onSuccess(final Transaction transaction) {
                //Log.i("transaction success", transaction.toString());

                if (mCallback != null) {
                    mCallback.onCallbackOrderReceived(transaction, null);
                }

                cancelLoaderId(1);
            }

            @Override
            public void onError(Exception error) {
                //Log.i("transaction failed", error.getLocalizedMessage());
                if (mCallback != null) {
                    mCallback.onCallbackOrderReceived(null, error);
                }
                cancelLoaderId(1);
            }
        });
    }

    @Override
    protected boolean isInputDataValid() {

        return true;
    }
}
