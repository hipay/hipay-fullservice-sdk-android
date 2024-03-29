package com.hipay.fullservice.screen.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.fullservice.core.models.PaymentMethod;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;

import java.util.Date;

/**
 * Created by HiPay on 15/05/16.
 */

public class ForwardPaymentFormFragment extends AbstractPaymentFormFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCardInfoLayout.setVisibility(View.GONE);

        //RelativeLayout layoutParams = (RelativeLayout) mProgressBar.getLayoutParams();
        //layoutParams.gravity = Gravity.CENTER;

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
        final String signature = args.getString(GatewayClient.SIGNATURE_TAG);

        OrderRequest orderRequest = new OrderRequest(paymentPageRequest);
        orderRequest.setPaymentProductCode(paymentProduct.getCode());

        mGatewayClient = new GatewayClient(getActivity());
        mCurrentLoading = AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue();

        final Date requestDate = new Date();

        mGatewayClient.requestNewOrder(orderRequest, signature, new OrderRequestCallback() {

            @Override
            public void onSuccess(final Transaction transaction) {
                if (mCallback != null) {
                    cancelLoaderId(AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue());
                    mCallback.onCallbackOrderReceived(transaction, null);
                }

            }

            @Override
            public void onError(Exception error) {
                //Log.i("transaction failed", error.getLocalizedMessage());
                if (mCallback != null) {
                    cancelLoaderId(AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue());
                    mCallback.onCallbackOrderReceived(null, error);
                }
            }
        });
    }

    @Override
    protected boolean isInputDataValid() {

        return true;
    }

    @Override
    public void savePaymentMethod(PaymentMethod method) {
        // do nothing
    }
}
