package com.hipay.fullservice.screen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.PaymentPageRequestCallback;
import com.hipay.fullservice.core.models.HostedPaymentPage;
import com.hipay.fullservice.core.models.PaymentMethod;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.screen.activity.ForwardWebViewActivity;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.net.URL;
import java.util.Collections;

/**
 * Created by nfillion on 20/04/16.
 */
public class UnsupportedPaymentFormFragment extends AbstractPaymentFormFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mCardInfoLayout.setVisibility(View.GONE);

        if (getLoadingMode() == false) {
            setLoadingMode(true, false);
            launchRequest();
        }
    }

    @Override
    protected boolean isInputDataValid() {
        return true;
    }

    @Override
    public void savePaymentMethod(PaymentMethod method)
    {
        //no-op
    }

    @Override
    public void onResume() {
        super.onResume();
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

    public void launchRequest() {

        Bundle args = getArguments();

        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
        final PaymentProduct paymentProduct = PaymentProduct.fromBundle(args.getBundle(PaymentProduct.TAG));

        final String signature = args.getString(GatewayClient.SIGNATURE_TAG);

        PaymentPageRequest pageRequest = new PaymentPageRequest(paymentPageRequest);

        //everything already empty
        //pageRequest.setPaymentProductCategoryList(null);
        pageRequest.setPaymentProductList(Collections.singletonList(paymentProduct.getCode()));
        pageRequest.setDisplaySelector(false);
        pageRequest.setTemplateName(PaymentPageRequest.PaymentPageRequestTemplateNameFrame);

        this.cancelOperations();
        mGatewayClient = new GatewayClient(getActivity());
        mCurrentLoading = AbstractClient.RequestLoaderId.PaymentPageReqLoaderId.getIntegerValue();
        mGatewayClient.createHostedPaymentPageRequest(pageRequest, signature, new PaymentPageRequestCallback() {

            @Override
            public void onError(Exception error) {

                if (mCallback != null) {
                    cancelLoaderId(AbstractClient.RequestLoaderId.PaymentPageReqLoaderId.getIntegerValue());
                    mCallback.onCallbackOrderReceived(null, error);
                }
            }

            @Override
            public void onSuccess(HostedPaymentPage hostedPaymentPage) {

                mLoadingMode = false;
                cancelLoaderId(AbstractClient.RequestLoaderId.PaymentPageReqLoaderId.getIntegerValue());
                if (mCallback != null) {

                    URL forwardUrl = hostedPaymentPage.getForwardUrl();
                    if (forwardUrl != null) {

                        final Bundle customThemeBundle = getArguments().getBundle(CustomTheme.TAG);
                        ForwardWebViewActivity.start(getActivity(), forwardUrl.toString(), paymentProduct.getPaymentProductDescription(), customThemeBundle);
                    }
                }
            }
        });
    }
}
