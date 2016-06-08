package com.hipay.hipayfullservice.screen.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.client.GatewayClient;
import com.hipay.hipayfullservice.core.client.SecureVaultClient;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.TransactionDetailsCallback;
import com.hipay.hipayfullservice.core.client.interfaces.callbacks.TransactionsDetailsCallback;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.screen.activity.ForwardWebViewActivity;
import com.hipay.hipayfullservice.screen.fragment.interfaces.CardBehaviour;
import com.hipay.hipayfullservice.screen.model.CustomTheme;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by nfillion on 29/02/16.
 */

public abstract class AbstractPaymentFormFragment extends Fragment {

    private static final String STATE_IS_LOADING = "isLoading";
    private static final String CURRENT_LOADER_ID = "currentLoaderId";

    private ProgressBar mProgressBar;
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

    private Button mPayButton;
    private FrameLayout mPayButtonLayout;

    protected GatewayClient mGatewayClient;
    protected SecureVaultClient mSecureVaultClient;

    protected String inferedPaymentProduct;
    protected CardBehaviour mCardBehaviour;

    public static AbstractPaymentFormFragment newInstance(Bundle paymentPageRequestBundle, PaymentProduct paymentProduct, Bundle customTheme) {

        AbstractPaymentFormFragment fragment;

        Boolean isTokenizable = paymentProduct.isTokenizable();

        if (isTokenizable != null && isTokenizable == true) {

            fragment = new TokenizableCardPaymentFormFragment();

        } else if (forwardProductsCodes().contains(paymentProduct.getCode()) ) {

            fragment = new ForwardPaymentFormFragment();

        } else {

            fragment = new UnsupportedPaymentFormFragment();
        }

        Bundle args = new Bundle();
        args.putBundle(PaymentPageRequest.TAG, paymentPageRequestBundle);
        args.putBundle(PaymentProduct.TAG, paymentProduct.toBundle());
        args.putBundle(CustomTheme.TAG, customTheme);

        fragment.setArguments(args);

        return fragment;
    }

    private static List<String> forwardProductsCodes() {

        List<String> forwardProductsCode = new ArrayList<>(Arrays.asList(
                PaymentProduct.PaymentProductCodePayPal,
                PaymentProduct.PaymentProductCodeYandex,
                PaymentProduct.PaymentProductCodeSofortUberweisung,
                PaymentProduct.PaymentProductCodeSisal,
                PaymentProduct.PaymentProductCodeSDD,
                PaymentProduct.PaymentProductCodePayULatam,
                PaymentProduct.PaymentProductCodeINGHomepay,
                PaymentProduct.PaymentProductCodeBCMCMobile,
                PaymentProduct.PaymentProductCodePrzelewy24,
                PaymentProduct.PaymentProductCodeBankTransfer,
                PaymentProduct.PaymentProductCodePaysafecard,
                PaymentProduct.PaymentProductCodeDexiaDirectNet
        ));

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
        Log.i("onActivityCreated", "onActivityCreated");


        // ---- magic lines starting here -----
        // call this to re-connect with an existing
        // loader (after screen configuration changes for e.g!)

        if (mSecureVaultClient != null && mCurrentLoading == 0) {
            mSecureVaultClient.reLaunchOperations(0);
        }

        if (mGatewayClient != null && mCurrentLoading > 0 ) {
            mGatewayClient.reLaunchOperations(mCurrentLoading);
        }

        //TODO need to handle the loadInBackground elements

        //----- end magic lines -----


        //LoaderManager supportLoaderManager = getActivity().getSupportLoaderManager();
        //lm = getLoaderManager();
        //if (supportLoaderManager.getLoader(0) != null) {
        //supportLoaderManager.initLoader(0, null, getActivity());
        //}
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

        if (savedInstanceState != null) {
            mLoadingMode = savedInstanceState.getBoolean(STATE_IS_LOADING);
            mCurrentLoading = savedInstanceState.getInt(CURRENT_LOADER_ID, -1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        validatePayButton(isInputDataValid());
        this.setLoadingMode(mLoadingMode);
    }

    public boolean getLoadingMode() {
        return mLoadingMode;
    }

    public void setLoadingMode(boolean loadingMode) {

        if (loadingMode) {

            mPayButtonLayout.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);

        } else {

            mPayButtonLayout.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }

        mLoadingMode = loadingMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View contentView = inflater.inflate(R.layout.fragment_payment_form, container, false);
        return contentView;
    }

    public void launchHostedPaymentPage(String forwardURLString) {

        final Bundle customThemeBundle = getArguments().getBundle(CustomTheme.TAG);
        ForwardWebViewActivity.start(getActivity(), forwardURLString, customThemeBundle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(STATE_IS_LOADING, mLoadingMode);
        outState.putInt(CURRENT_LOADER_ID, mCurrentLoading);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initContentViews(view);

    }

    private StateListDrawable makeSelector(CustomTheme theme) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(ContextCompat.getColor(getActivity(), theme.getColorPrimaryDarkId())));
        res.addState(new int[]{}, new ColorDrawable(ContextCompat.getColor(getActivity(), theme.getColorPrimaryId())));
        return res;
    }

    protected void initContentViews(View view) {

        mPayButton = (Button) view.findViewById(R.id.pay_button);
        mPayButtonLayout = (FrameLayout) view.findViewById(R.id.pay_button_layout);

        Bundle args = getArguments();
        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        Currency c = Currency.getInstance(paymentPageRequest.getCurrency());
        currencyFormatter.setCurrency(c);
        String moneyFormatted = currencyFormatter.format(paymentPageRequest.getAmount());

        String moneyString = getString(R.string.pay, moneyFormatted);

        mPayButton.setText(moneyString);

        mPayButtonLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setLoadingMode(true);
                launchRequest();
            }
        });

        mProgressBar = (ProgressBar) view.findViewById(R.id.empty);
        mCardInfoLayout = (LinearLayout) view.findViewById(R.id.card_info_layout);

    }

    public void launchBackgroundReload(Transaction transaction) {

        if (transaction != null) {

            String transactionReference = transaction.getTransactionReference();

            mCurrentLoading = 3;
            new GatewayClient(getActivity())
                    .getTransactionWithReference(transactionReference, new TransactionDetailsCallback() {

                        @Override
                        public void onSuccess(final Transaction transaction) {
                            Log.i("transaction success", transaction.toString());

                            if (mCallback != null) {
                                mCallback.onCallbackOrderReceived(transaction, null);
                            }
                            cancelLoaderId(3);
                        }

                        @Override
                        public void onError(Exception error) {
                            Log.i("transaction failed", error.getLocalizedMessage());
                            if (mCallback != null) {
                                mCallback.onCallbackOrderReceived(null, error);
                            }
                            cancelLoaderId(3);
                        }
                    });
        }
        else {

            Bundle args = getArguments();
            final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));

            String orderId = paymentPageRequest.getOrderId();
            mCurrentLoading = 4;
            new GatewayClient(getActivity())
                    .getTransactionsWithOrderId(orderId, new TransactionsDetailsCallback() {

                        @Override
                        public void onSuccess(List<Transaction> transactions) {

                            Log.i("transaction success", transactions.toString());

                            if (mCallback != null) {
                                mCallback.onCallbackOrderReceived(transactions.get(0), null);
                            }
                            cancelLoaderId(4);
                        }


                        @Override
                        public void onError(Exception error) {
                            Log.i("transaction failed", error.getLocalizedMessage());
                            if (mCallback != null) {
                                mCallback.onCallbackOrderReceived(null, error);
                            }
                            cancelLoaderId(4);
                        }
                    });
        }
    }

    protected void validatePayButton(boolean validate) {

        if (validate) {

            final Bundle customThemeBundle = getArguments().getBundle(CustomTheme.TAG);
            CustomTheme theme = CustomTheme.fromBundle(customThemeBundle);

            mPayButton.setTextColor(ContextCompat.getColor(getActivity(), theme.getTextColorPrimaryId()));
            mPayButtonLayout.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                //mPayButton.setBackground(makeSelector(theme));
                mPayButtonLayout.setBackground(makeSelector(theme));

                Drawable[] drawables = mPayButton.getCompoundDrawables();
                Drawable wrapDrawable = DrawableCompat.wrap(drawables[0]);
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getActivity(), theme.getTextColorPrimaryId()));
            }

        } else {

            //TODO inactive button
            mPayButton.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
            mPayButtonLayout.setEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                CustomTheme greyTheme = new CustomTheme(R.color.dark_grey, R.color.dark_grey, R.color.dark_grey);
                //mPayButton.setBackground(makeSelector(greyTheme));
                mPayButtonLayout.setBackground(makeSelector(greyTheme));
                //mPayButton.getDra

                Drawable[] drawables = mPayButton.getCompoundDrawables();
                Drawable wrapDrawable = DrawableCompat.wrap(drawables[0]);
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getActivity(), android.R.color.white));
                //DrawableCompat.setTint(wrapDrawable, getResources().getColor(android.R.color.holo_red_dark));
            }
        }
    }

    protected void cancelLoaderId(int loaderId) {

        mCurrentLoading = -1;

        switch (loaderId) {

            //securevault
            case 0: {

                if (mSecureVaultClient != null) {
                    mSecureVaultClient.cancelOperation();
                    mSecureVaultClient = null;
                }

            } break;

            //order
            case 1: {

                if (mGatewayClient != null) {
                    mGatewayClient.cancelOperation();
                    mGatewayClient = null;
                }

            } break;

            //paymentPageRequest
            case 2: {

                if (mGatewayClient != null) {
                    mGatewayClient.cancelOperation();
                    mGatewayClient = null;
                }

            } break;


            //transaction reference
            case 3: {

                if (mGatewayClient != null) {
                    mGatewayClient.cancelOperation();
                    mGatewayClient = null;
                }

            } break;

            //transaction orderid
            case 4: {

                if (mGatewayClient != null) {
                    mGatewayClient.cancelOperation();
                    mGatewayClient = null;
                }

            } break;
        }
    }

    public void cancelOperations() {

        if (mGatewayClient != null) {
            mGatewayClient.cancelOperation();
            mGatewayClient = null;
        }

        if (mSecureVaultClient != null) {
            mSecureVaultClient.cancelOperation();
            mSecureVaultClient = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //cancelOperations();
    }


    protected abstract boolean isInputDataValid();
 }
