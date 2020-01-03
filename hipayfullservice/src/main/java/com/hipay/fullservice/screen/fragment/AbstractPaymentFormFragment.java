package com.hipay.fullservice.screen.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.client.SecureVaultClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.TransactionDetailsCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.TransactionsDetailsCallback;
import com.hipay.fullservice.core.models.PaymentMethod;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.screen.activity.ForwardWebViewActivity;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.util.Arrays;
import java.util.List;

/**
 * Created by HiPay on 29/02/16.
 */

public abstract class AbstractPaymentFormFragment extends Fragment {

    protected ProgressBar mProgressBar;
    OnCallbackOrderListener mCallback;
    protected LinearLayout mCardInfoLayout;

    protected boolean mLoadingMode = false;
    protected int mCurrentLoading = -1;

    public abstract void launchRequest();
    public interface OnCallbackOrderListener {

        void onCallbackOrderReceived(Transaction transaction, Exception exception);
        void updatePaymentProduct(String title);
        void dismissDialogs();
    }

    protected TextInputEditText mCardOwner;
    protected TextInputEditText mCardNumber;
    protected TextInputEditText mCardExpiration;
    protected TextInputEditText mCardCVV;

    protected TextInputLayout mCardCVVLayout;
    protected TextInputLayout mCardNumberLayout;
    protected TextInputLayout mCardExpirationLayout;
    protected TextInputLayout mCardOwnerLayout;

    protected LinearLayout mSecurityCodeInfoLayout;
    protected TextView mSecurityCodeInfoTextview;
    protected ImageView mSecurityCodeInfoImageview;

    protected GatewayClient mGatewayClient;
    protected SecureVaultClient mSecureVaultClient;


    protected abstract boolean isInputDataValid();
    public abstract void savePaymentMethod(PaymentMethod method);

    public static AbstractPaymentFormFragment newInstance(Bundle paymentPageRequestBundle, PaymentProduct paymentProduct, String signature, Bundle customTheme) {

        AbstractPaymentFormFragment fragment;

        Boolean isTokenizable = paymentProduct.isTokenizable();

        if (isTokenizable != null && isTokenizable == true) {

            fragment = new TokenizableCardPaymentFormFragment();

        } else if (paymentProduct.getCode().equals(PaymentProduct.PaymentProductCodeSDD)) {

            fragment = new IBANFormFragment();

        } else if (forwardProductsCodes().contains(paymentProduct.getCode()) ) {

            fragment = new ForwardPaymentFormFragment();

        } else {

            fragment = new UnsupportedPaymentFormFragment();
        }

        Bundle args = new Bundle();
        args.putBundle(PaymentPageRequest.TAG, paymentPageRequestBundle);
        args.putBundle(PaymentProduct.TAG, paymentProduct.toBundle());
        args.putBundle(CustomTheme.TAG, customTheme);
        args.putString(GatewayClient.SIGNATURE_TAG, signature);

        fragment.setArguments(args);

        return fragment;
    }

    private static List<String> forwardProductsCodes() {

        List<String> forwardProductsCode = Arrays.asList(
                PaymentProduct.PaymentProductCodePayPal,
                PaymentProduct.PaymentProductCodeYandex,
                PaymentProduct.PaymentProductCodeSofortUberweisung,
                PaymentProduct.PaymentProductCodeSisal,
                PaymentProduct.PaymentProductCodePayULatam,
                PaymentProduct.PaymentProductCodeINGHomepay,
                PaymentProduct.PaymentProductCodeBCMCMobile,
                PaymentProduct.PaymentProductCodePrzelewy24,
                PaymentProduct.PaymentProductCodeBankTransfer,
                PaymentProduct.PaymentProductCodePaysafecard,
                PaymentProduct.PaymentProductCodeDexiaDirectNet
        );

        return forwardProductsCode;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnCallbackOrderListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
            + " must implement OnCallbackOrderListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ---- magic lines starting here -----
        // call this to re-connect with an existing
        // loader (after screen configuration changes for e.g!)

        if (mSecureVaultClient != null && mCurrentLoading == AbstractClient.RequestLoaderId.GenerateTokenReqLoaderId.getIntegerValue()) {

            if (mSecureVaultClient.canRelaunch()) {
                mSecureVaultClient.reLaunchOperations(AbstractClient.RequestLoaderId.GenerateTokenReqLoaderId.getIntegerValue());

            } else {

                cancelLoaderId(AbstractClient.RequestLoaderId.GenerateTokenReqLoaderId.getIntegerValue());

                //check if launchRequest
                cancelOperations();
                launchRequest();
            }
        }

        if (mGatewayClient != null && mCurrentLoading > AbstractClient.RequestLoaderId.GenerateTokenReqLoaderId.getIntegerValue() ) {

            if (mGatewayClient.canRelaunch()) {
                mGatewayClient.reLaunchOperations(mCurrentLoading);

            } else {

                //cancelLoaderId(mCurrentLoading);
                cancelOperations();
                launchRequest();
            }
        }

        //----- end magic lines -----
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //important for the magic lines
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.setLoadingMode(mLoadingMode, false);
    }

    public boolean getLoadingMode() {
        return mLoadingMode;
    }

    public abstract void setLoadingMode(boolean loadingMode, boolean delay);


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View contentView = inflater.inflate(R.layout.fragment_payment_form, container, false);
        return contentView;
    }

    public void launchHostedPaymentPage(String forwardURLString, String title) {

        final Bundle customThemeBundle = getArguments().getBundle(CustomTheme.TAG);
        ForwardWebViewActivity.start(getActivity(), forwardURLString, title, customThemeBundle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initContentViews(view);

    }

    protected void initContentViews(View view) {

        mProgressBar = (ProgressBar) view.findViewById(R.id.empty);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.hpf_accent)));

        } else {
            mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.hpf_accent), PorterDuff.Mode.SRC_IN);
        }
        mCardInfoLayout = (LinearLayout) view.findViewById(R.id.card_info_layout);

    }

    public void launchBackgroundReload(Transaction transaction) {

        if (transaction != null) {

            String transactionReference = transaction.getTransactionReference();

            Bundle args = getArguments();
            final String signature = args.getString(GatewayClient.SIGNATURE_TAG);

            mCurrentLoading = AbstractClient.RequestLoaderId.TransactionReqLoaderId.getIntegerValue();
            mGatewayClient = new GatewayClient(getActivity());
                    mGatewayClient.getTransactionWithReference(transactionReference, signature, new TransactionDetailsCallback() {

                        @Override
                        public void onSuccess(final Transaction transaction) {

                            if (mCallback != null) {
                                cancelLoaderId(AbstractClient.RequestLoaderId.TransactionReqLoaderId.getIntegerValue());
                                mCallback.onCallbackOrderReceived(transaction, null);
                            }
                        }

                        @Override
                        public void onError(Exception error) {

                            if (mCallback != null) {
                                cancelLoaderId(AbstractClient.RequestLoaderId.TransactionReqLoaderId.getIntegerValue());
                                mCallback.onCallbackOrderReceived(null, error);
                            }
                        }
                    });
        }
        else {

            Bundle args = getArguments();
            final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
            final String signature = args.getString(GatewayClient.SIGNATURE_TAG);

            String orderId = paymentPageRequest.getOrderId();
            mCurrentLoading = AbstractClient.RequestLoaderId.TransactionsReqLoaderId.getIntegerValue();
            mGatewayClient = new GatewayClient(getActivity());
            mGatewayClient.getTransactionsWithOrderId(orderId, signature, new TransactionsDetailsCallback() {

                @Override
                public void onSuccess(List<Transaction> transactions) {
                    if (mCallback != null) {
                        cancelLoaderId(AbstractClient.RequestLoaderId.TransactionsReqLoaderId.getIntegerValue());
                        mCallback.onCallbackOrderReceived(transactions.get(0), null);
                    }
                }

                @Override
                public void onError(Exception error) {
                    if (mCallback != null) {
                        cancelLoaderId(AbstractClient.RequestLoaderId.TransactionsReqLoaderId.getIntegerValue());
                        mCallback.onCallbackOrderReceived(null, error);
                    }
                }
            });
        }
    }

    protected void cancelLoaderId(int loaderId) {

        mCurrentLoading = -1;

        switch (loaderId) {

            //securevault generateToken
            case 0: {

                if (mSecureVaultClient != null) {
                    mSecureVaultClient.cancelOperation(getActivity());
                    mSecureVaultClient = null;
                }

            } break;

            //anything else
            default: {

                if (mGatewayClient != null) {
                    mGatewayClient.cancelOperation(getActivity());
                    mGatewayClient = null;
                }
            }
        }
    }

    public void cancelOperations() {

        if (mGatewayClient != null) {
            mGatewayClient.cancelOperation(getActivity());
            mGatewayClient = null;
        }

        if (mSecureVaultClient != null) {
            mSecureVaultClient.cancelOperation(getActivity());
            mSecureVaultClient = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.cancelOperations();
    }
}
