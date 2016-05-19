package com.hipay.hipayfullservice.screen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hipay.hipayfullservice.R;
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

/**
 * Created by nfillion on 20/04/16.
 */
public class TokenizableCardPaymentFormFragment extends AbstractPaymentFormFragment {

    private String inferedPaymentProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initContentViews(View view) {
        super.initContentViews(view);

        EditText cardExpiryEditText = (EditText) view.findViewById(R.id.card_expiration);
        cardExpiryEditText.setText("12/34");

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
        String paymentProductCode = paymentProduct.getCode();

        if (
                paymentProductCode.equals(PaymentProduct.PaymentProductCodeBCMC)) {

            //hide the security code for bcmc
            TextInputLayout editTextCVVsupport = (TextInputLayout) view.findViewById(R.id.card_cvv_support);
            editTextCVVsupport.setVisibility(View.GONE);

            //bcmc
            //"67030000000000003",
            mCardNumber.setText("67030000000000003");
            mCardCVV.setText("123");

            // replace CVV by CID

        } else if (paymentProductCode.equals(PaymentProduct.PaymentProductCodeMaestro)) {

            TextInputLayout editTextCVVsupport = (TextInputLayout) view.findViewById(R.id.card_cvv_support);
            editTextCVVsupport.setVisibility(View.GONE);

            //Maestro
            //"6759411100000008",
            mCardCVV.setText("123");
            mCardNumber.setText("6759411100000008");

        } else if (paymentProductCode.equals(PaymentProduct.PaymentProductCodeAmericanExpress)) {

            //nothing for amex

            //card_cvv
            //security code is 4 digits long for amex
            mCardCVV.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            //Mastercard
            //"371244931714724",
            mCardNumber.setText("371244931714724");
            mCardCVV.setText("1234");

        } else if (paymentProductCode.equals(PaymentProduct.PaymentProductCodeVisa) ||
                paymentProductCode.equals(PaymentProduct.PaymentProductCodeCB)) {

            //visa
            //"4111111111111111",
            //"4000000000000002",
            //"4111113333333333",
            mCardNumber.setText("4111111111111111");
            mCardCVV.setText("123");

            //3056 991822 5904


        } else if (paymentProductCode.equals(PaymentProduct.PaymentProductCodeMasterCard)) {

            //Mastercard
            //"5399999999999999",
            mCardNumber.setText("5399999999999999");
            mCardCVV.setText("123");

        } else if (paymentProductCode.equals(PaymentProduct.PaymentProductCodeDiners)) {

            //no test card
            //Diners Club International
            //"36082634567890"
            //"30569309025904"
            //"38520000023237"
            mCardNumber.setText("38520000023237");
            mCardCVV.setText("123");
        }
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

                //visa
                //"4111111111111111",
                //"4000000000000002",
                //"4111113333333333",

                //Mastercard
                //"5399999999999999",

                //Maestro
                //"6759411100000008",

                //bcmc
                //"67030000000000003",

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

