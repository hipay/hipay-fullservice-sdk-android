package com.hipay.hipayfullservice.screen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hipay.hipayfullservice.core.client.GatewayClient;
import com.hipay.hipayfullservice.core.client.SecureVaultClient;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.hipayfullservice.core.models.PaymentCardToken;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.hipayfullservice.core.requests.order.OrderRequest;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.hipayfullservice.screen.fragment.interfaces.CardBehaviour;

/**
 * Created by nfillion on 20/04/16.
 */
public class TokenizableCardPaymentFormFragment extends AbstractPaymentFormFragment {

    private String inferedPaymentProduct;
    private CardBehaviour cardBehaviour;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initContentViews(View view) {
        super.initContentViews(view);

        mCardInfoLayout.setVisibility(View.VISIBLE);

        Bundle args = getArguments();
        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));

        CustomerInfoRequest customerInfoRequest = paymentPageRequest.getCustomer();
        String displayName = customerInfoRequest.getDisplayName();
        if (displayName != null) {
            mCardOwner.setText(paymentPageRequest.getCustomer().getDisplayName());
        }

        // check every product code to know how to handle the editTexts
        final PaymentProduct paymentProduct = PaymentProduct.fromBundle(args.getBundle(PaymentProduct.TAG));
        cardBehaviour = new CardBehaviour(paymentProduct);
        cardBehaviour.updateForm(mCardNumber, mCardCVV, mCardCVVLayout);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void launchRequest() {

        Bundle args = getArguments();

        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
        final PaymentProduct paymentProduct = PaymentProduct.fromBundle(args.getBundle(PaymentProduct.TAG));

        mSecureVaultClient = new SecureVaultClient(getActivity());
        mCurrentLoading = 0;
        mSecureVaultClient.createTokenRequest(

                mCardNumber.getText().toString(),

                this.getMonthFromExpiry(mCardExpiration.getText().toString()),
                this.getYearFromExpiry(mCardExpiration.getText().toString()),
                mCardOwner.getText().toString(),
                mCardCVV.getText().toString(),
                //null,
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

                        //secure vault
                        cancelLoaderId(0);

                        //check if activity is still available
                        if (getActivity() != null) {

                            mGatewayClient = new GatewayClient(getActivity());
                            mCurrentLoading = 1;
                            mGatewayClient.createOrderRequest(orderRequest, new OrderRequestCallback() {

                                @Override
                                public void onSuccess(final Transaction transaction) {
                                    Log.i("transaction success", transaction.toString());

                                    if (mCallback != null) {
                                        mCallback.onCallbackOrderReceived(transaction, null);
                                    }

                                    cancelLoaderId(1);
                                }

                                @Override
                                public void onError(Exception error) {
                                    Log.i("transaction failed", error.getLocalizedMessage());
                                    if (mCallback != null) {
                                        mCallback.onCallbackOrderReceived(null, error);
                                    }
                                    cancelLoaderId(1);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Exception error) {

                        if (mCallback != null) {
                            mCallback.onCallbackOrderReceived(null, error);
                        }
                        cancelLoaderId(0);
                    }
                }
        );
    }

    private String getMonthFromExpiry(String expiryString) {

        return expiryString.substring(0,2);
    }

    private String getYearFromExpiry(String expiryString) {

        String year = null;
        if (expiryString.length() == 5 && expiryString.charAt(2) == '/') {
            year = expiryString.substring(3,5);

        } else if (expiryString.length() == 4 && TextUtils.isDigitsOnly(expiryString)) {
            year = expiryString.substring(2,4);
        }

        if (year != null) {
            return String.valueOf(normalizeYear(Integer.valueOf(year)));
        }

        return null;
    }

    @Override
    protected boolean isInputDataValid() {

        //TODO behaviour will handle that

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

