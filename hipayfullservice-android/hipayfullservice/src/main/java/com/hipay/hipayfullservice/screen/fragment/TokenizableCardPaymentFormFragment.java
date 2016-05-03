package com.hipay.hipayfullservice.screen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hipay.hipayfullservice.core.client.GatewayClient;
import com.hipay.hipayfullservice.core.client.SecureVaultClient;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.hipayfullservice.core.models.PaymentCardToken;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;

/**
 * Created by nfillion on 20/04/16.
 */
public class TokenizableCardPaymentFormFragment extends AbstractPaymentFormFragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        mCardInfoLayout.setVisibility(View.VISIBLE);
        //TODO put this paymentPageRequest as args to get it.
    }


    @Override
    protected void launchRequest() {

        Bundle args = getArguments();

        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
        final PaymentProduct paymentProduct = PaymentProduct.fromBundle(args.getBundle(PaymentProduct.TAG));

        SecureVaultClient client = new SecureVaultClient(getActivity());
        client.createTokenRequest(
                mCardNumber.getText().toString(),
                //"4111111111111111",
                "12",
                "2019",
                mCardOwner.getText().toString(),
                mCardCVV.getText().toString(),
                false,

                new SecureVaultRequestCallback() {
                    @Override
                    public void onSuccess(PaymentCardToken paymentCardToken) {

                        Log.i(paymentCardToken.toString(), paymentCardToken.toString());

                        OrderRequest orderRequest = new OrderRequest(paymentPageRequest);
                        orderRequest.setPaymentProductCode(paymentProduct.getCode());

                        CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest =
                                new CardTokenPaymentMethodRequest(
                                        paymentCardToken.getToken(),
                                        paymentPageRequest.getEci(),
                                        paymentPageRequest.getAuthenticationIndicator());

                        orderRequest.setPaymentMethod(cardTokenPaymentMethodRequest);

                        //check if activity is still available
                        if (getActivity() != null) {

                            new GatewayClient(getActivity())
                                    .createOrderRequest(orderRequest, new OrderRequestCallback() {

                                        @Override
                                        public void onSuccess(final Transaction transaction) {
                                            Log.i("transaction success", transaction.toString());

                                            if (mCallback != null) {
                                                mCallback.onCallbackOrderReceived(transaction, null);
                                            }
                                        }

                                        @Override
                                        public void onError(Exception error) {
                                            Log.i("transaction failed", error.getLocalizedMessage());
                                            if (mCallback != null) {
                                                mCallback.onCallbackOrderReceived(null, error);
                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onError(Exception error) {

                        if (mCallback != null) {
                            mCallback.onCallbackOrderReceived(null, error);
                        }
                    }
                }
        );
    }


    @Override
    protected boolean isInputDataValid() {

        if (
                this.isExpiryDateValid() &&
                this.isCVVValid() &&
                this.isCardOwnerValid() &&
                this.isCardNumberValid()

                ) {

            return true;
        }

        return false;
    }

}

