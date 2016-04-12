package com.hipay.hipayfullservice.screen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hipay.hipayfullservice.core.client.GatewayClient;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.PaymentPageRequestCallback;
import com.hipay.hipayfullservice.core.models.HostedPaymentPage;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.screen.activity.ForwardWebViewActivity;
import com.hipay.hipayfullservice.screen.model.CustomTheme;

import java.util.ArrayList;

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
        //TODO put this paymentPageRequest as args to get it.
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void launchRequest() {

        //TODO on tente le hpayment

        Bundle args = getArguments();

        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
        final PaymentProduct paymentProduct = PaymentProduct.fromBundle(args.getBundle(PaymentProduct.TAG));

        PaymentPageRequest pageRequest = new PaymentPageRequest(paymentPageRequest);
        pageRequest.setPaymentProductCategoryList(null);

        ArrayList<String> codes = new ArrayList<>(1);
        codes.add(paymentProduct.getCode());

        pageRequest.setPaymentProductList(codes);
        pageRequest.setDisplaySelector(false);
        pageRequest.setTemplateName(PaymentPageRequest.PaymentPageRequestTemplateNameFrame);

        this.cancelOperation();
        mGatewayClient = new GatewayClient(getActivity());
        mGatewayClient.createHostedPaymentPageRequest(paymentPageRequest, new PaymentPageRequestCallback() {

            @Override
            public void onError(Exception error) {

                if (mCallback != null) {
                    mCallback.onCallbackOrderReceived(null, error);
                }
            }

            @Override
            public void onSuccess(HostedPaymentPage hostedPaymentPage) {

                if (getActivity() != null) {

                    final Bundle customThemeBundle = getArguments().getBundle(CustomTheme.TAG);
                    ForwardWebViewActivity.start(getActivity(), hostedPaymentPage, customThemeBundle);
                }
            }
        });
    }
}
