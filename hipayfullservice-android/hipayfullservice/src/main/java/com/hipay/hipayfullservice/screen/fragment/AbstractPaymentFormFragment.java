package com.hipay.hipayfullservice.screen.fragment;

import android.app.Activity;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.hipay.hipayfullservice.core.utils.DataExtractor;
import com.hipay.hipayfullservice.screen.activity.ForwardWebViewActivity;
import com.hipay.hipayfullservice.screen.helper.FormHelper;
import com.hipay.hipayfullservice.screen.model.CustomTheme;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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

    protected Set<String> paymentProductCodes;

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

        setRetainInstance(true);

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

        if (savedInstanceState != null) {
            mLoadingMode = savedInstanceState.getBoolean(STATE_IS_LOADING);
            mCurrentLoading = savedInstanceState.getInt(CURRENT_LOADER_ID, -1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        validatePayButton(isInputDataValid());
        putEverythingInRed();

        this.setLoadingMode(mLoadingMode);
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

    private class GenericTextWatcher implements TextWatcher {

        private int diffLength = 0;
        private int changeStart = 0;

        private View v;
        private GenericTextWatcher(View view) {
            this.v = view;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            diffLength = after - count;
            changeStart = start;
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            diffLength = count - before;
            changeStart = start;
        }

        public void afterTextChanged(Editable editable) {

            int i = v.getId();

            if (i == R.id.card_owner) {

                putCardOwnerInRed(false);

            } else if (i == R.id.card_cvv) {

                // the condition to say it is wrong
                putCVVInRed(false);

            } else if (i == R.id.card_expiration) {

                //TODO handle editext expiration
                //check how to handle the add or suppression in the middle of the string
                //check how to handle a copy paste (remove x chars, add y chars...)

                String version = editable.toString();
                //user deletes data

                if (diffLength < 0) {

                    int posDifflength = - diffLength;

                    //length now
                    int newLength = version.length();
                    int oldLength = newLength + posDifflength;

                    if (newLength <= 2 && oldLength > 2) {
                        editable.delete(editable.length()-1, editable.length());
                    }

                } else {

                    if (version.length() == 1 && diffLength == 1 && changeStart == 0 && Integer.valueOf(version) > 1  ) {

                        editable.insert(0, "0");
                    }

                    if (!version.contains("/")) {

                        if (version.length() >= 2) {
                            editable.insert(2, "/");
                        }
                    }
                }

                if (editable.length() == 5) {
                    putExpiryDateInRed(true);

                } else {
                    putExpiryDateInRed(false);
                }


            } else if (i == R.id.card_number) {

                //paymentProductCodes = FormHelper.getPaymentProductCodes(mCardNumber.getText().toString(), getActivity());
                putCardNumberInRed(false);

            } else {
                throw new UnsupportedOperationException(
                        "OnClick has not been implemented for " + getResources().
                                getResourceName(v.getId()));
            }
            validatePayButton(isInputDataValid());
        }
    }

    private StateListDrawable makeSelector(CustomTheme theme) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(ContextCompat.getColor(getActivity(), theme.getColorPrimaryDarkId())));
        res.addState(new int[]{}, new ColorDrawable(ContextCompat.getColor(getActivity(), theme.getColorPrimaryId())));
        return res;
    }

    protected void initContentViews(View view) {

        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                int i = v.getId();

                if (i == R.id.card_owner) {

                    putCardOwnerInRed(hasFocus);

                } else if (i == R.id.card_cvv) {

                    putCVVInRed(!hasFocus);

                } else if (i == R.id.card_expiration) {

                    putExpiryDateInRed(!hasFocus);

                } else if (i == R.id.card_number) {

                    //TODO
                    putCardNumberInRed(!hasFocus);

                } else {
                    throw new UnsupportedOperationException(
                            "onFocusChange has not been implemented for " + getResources().
                                    getResourceName(v.getId()));
                }
            }
        };

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

        mCardOwner = (TextInputEditText) view.findViewById(R.id.card_owner);

        mCardOwner.setOnFocusChangeListener(focusChangeListener);
        mCardOwner.addTextChangedListener(new GenericTextWatcher(mCardOwner));

        mCardNumber = (TextInputEditText) view.findViewById(R.id.card_number);
        mCardNumber.addTextChangedListener(new GenericTextWatcher(mCardNumber));
        mCardNumber.setOnFocusChangeListener(focusChangeListener);


//        InputFilter filter = new InputFilter() {
//            public CharSequence filter(CharSequence source, int start, int end,
//                                       Spanned dest, int dstart, int dend) {
//
//                for (int i = start; i < end; i++) {
//
//                    if (source.charAt(i) == ' ') {
//
//                        //return "k";
//                    }
//
//                }
                //return null;
            //}
        //};

        //mCardNumber.setFilters(new InputFilter[] { filter });

        /*
        mCardNumber.addTextChangedListener(new TextWatcher() {

            boolean addSlash = false;
            private static final char space = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                if (s.length() == 0) {
                    //mDoneFab.hide();
                }

                if (start == 4 && count == 0 && after == 1 && s.length() == 4) {
                    addSlash = true;
                } else {
                    addSlash = false;
                }

                if (start == 9 && count == 0 && after == 1 && s.length() == 4) {
                    addSlash = true;
                } else {
                    addSlash = false;
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // hiding the floating action button if text is empty
                if (s.length() == 0) {
                    //mDoneFab.hide();
                }

                if (start == 4 && before == 0 && count == 1 && s.length() == 5) {
                    addSlash = true;

                } else

                {
                    addSlash = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                validatePayButton(isInputDataValid());
                if (isInputDataValid()) {
                    //mDoneFab.show();
                }

                if (addSlash == true) {

                    s.insert(4, " ");
                }

                addSlash = false;

                // Remove spacing char
                //if (s.length() > 0 && (s.length() % 5) == 0) {
                    //final char c = s.charAt(s.length() - 1);
                    //if (space == c) {
                        //s.delete(s.length() - 1, s.length());
                    //}
                //}
                //// Insert char where needed.
                //if (s.length() > 0 && (s.length() % 5) == 0) {
                    //char c = s.charAt(s.length() - 1);
                    //// Only if its a digit where there should be a space we insert a space
                    //if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        //s.insert(s.length() - 1, String.valueOf(space));
                    //}
                //}
            }
        });
        */

        mCardExpiration = (TextInputEditText) view.findViewById(R.id.card_expiration);
        mCardExpiration.setOnFocusChangeListener(focusChangeListener);
        mCardExpiration.addTextChangedListener(new GenericTextWatcher(mCardExpiration));

        mCardCVV = (TextInputEditText) view.findViewById(R.id.card_cvv);
        mCardCVV.setOnFocusChangeListener(focusChangeListener);
        mCardCVV.addTextChangedListener(new GenericTextWatcher(mCardCVV));

        mCardCVVLayout = (TextInputLayout) view.findViewById(R.id.card_cvv_support);
        mCardOwnerLayout = (TextInputLayout) view.findViewById(R.id.card_owner_support);
        mCardExpirationLayout = (TextInputLayout) view.findViewById(R.id.card_expiration_support);
        mCardNumberLayout = (TextInputLayout) view.findViewById(R.id.card_number_support);

        mCardOwnerLayout.setError(" ");
        mCardNumberLayout.setError(" ");
        mCardExpirationLayout.setError(" ");
        mCardCVVLayout.setError(" ");

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

    private void validatePayButton(boolean validate) {

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

    protected void cancelOperations() {

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

    protected boolean isCVVValid() {

        if (!TextUtils.isEmpty(mCardCVV.getText())) {

            String cvvString = mCardCVV.getText().toString().trim();
            if (cvvString.length() >= 3 && TextUtils.isDigitsOnly(cvvString)) {

                //TODO is CVV always a 3 digits number?
                //TODO looks like mastercard needs 4 digits
                return true;
            }
        }
        return false;
    }

    protected boolean isCardOwnerValid() {

        if (!TextUtils.isEmpty(mCardOwner.getText())) {
            return true;
        }
        return false;
    }

    protected boolean isCardNumberValid() {

        if (!TextUtils.isEmpty(mCardNumber.getText())) {

            //luhn first
            if (!FormHelper.luhnTest(mCardNumber.getText().toString())) {
                return false;
            }

            //format then
            Set<String> paymentProducts = FormHelper.getPaymentProductCodes(mCardNumber.getText().toString(), getActivity());
            if (paymentProducts.isEmpty()) {
                return false;
            }

            if (paymentProducts.size() == 1) {

                String[] things = paymentProducts.toArray(new String[1]);

                if (FormHelper.hasValidCardLength(mCardNumber.getText().toString(), things[0], getActivity())) {

                    return true;
                }
            }
        }

        return false;
    }

    protected boolean isExpiryDateValid() {

        if (!TextUtils.isEmpty(mCardExpiration.getText())) {

            String expiryDateString = mCardExpiration.getText().toString().trim();

            if (expiryDateString.length() == 5 && expiryDateString.charAt(2) == '/') {

                String firstPart = expiryDateString.substring(0, 2);
                String secondPart = expiryDateString.substring(3,5);

                StringBuilder stringBuilder = new StringBuilder(firstPart).append(secondPart);
                String expiryDate = stringBuilder.toString();

                if (TextUtils.isDigitsOnly(expiryDate)) {

                    //TODO finish this
                    return validateExpiryDate(expiryDate);
                }

            } else if (expiryDateString.length() == 4 && TextUtils.isDigitsOnly(expiryDateString)) {

                return validateExpiryDate(expiryDateString);
            }
        }
        return false;
    }

    protected void putEverythingInRed() {

        this.putExpiryDateInRed(true);
        this.putCVVInRed(true);
        this.putCardOwnerInRed(true);
        this.putCardNumberInRed(true);
    }

    private void putExpiryDateInRed(boolean red) {

        int color;

        if (red && !this.isExpiryDateValid()) {
            color = R.color.design_textinput_error_color_light;
        } else {
            color = R.color.hpf_accent;
        }

        this.mCardExpiration.setTextColor(ContextCompat.getColor(getActivity(), color));
    }
    private void putCVVInRed(boolean red) {

        int color;

        if (red && !this.isCVVValid()) {
            color = R.color.design_textinput_error_color_light;

        } else {
            color = R.color.hpf_accent;
        }

        this.mCardCVV.setTextColor(ContextCompat.getColor(getActivity(), color));
    }
    private void putCardOwnerInRed(boolean red) {

        int color;

        if (red && !this.isCardOwnerValid()) {
            color = R.color.design_textinput_error_color_light;

        } else {
            color = R.color.hpf_accent;
        }

        this.mCardOwner.setTextColor(ContextCompat.getColor(getActivity(), color));
    }
    private void putCardNumberInRed(boolean red) {

        int color;

        if (red && !this.isCardNumberValid()) {
            color = R.color.design_textinput_error_color_light;

        } else {
            color = R.color.hpf_accent;
        }

        this.mCardNumber.setTextColor(ContextCompat.getColor(getActivity(), color));
    }

    public boolean validateExpiryDate(String expiryDate) {

        if (expiryDate.length() != 4 || !TextUtils.isDigitsOnly(expiryDate)) {
            return false;
        }

        Integer expMonth = Integer.valueOf(expiryDate.substring(0,2));
        Integer expYear = Integer.valueOf(expiryDate.substring(2,4));

        if (!validateExpMonth(expMonth)) {
            return false;
        }

        if (!validateExpYear(expYear)) {
            return false;
        }

        return !hasMonthPassed(expYear, expMonth);
    }

    public boolean validateExpMonth(Integer expMonth) {
        if (expMonth == null) {
            return false;
        }
        return (expMonth >= 1 && expMonth <= 12);
    }

    public boolean validateExpYear(Integer expYear) {
        if (expYear == null) {
            return false;
        }
        return !hasYearPassed(expYear);
    }

    protected boolean hasYearPassed(int year) {
        int normalized = normalizeYear(year);
        Calendar now = Calendar.getInstance();
        return normalized < now.get(Calendar.YEAR);
    }

    protected boolean hasMonthPassed(int year, int month) {
        Calendar now = Calendar.getInstance();
        // Expires at end of specified month, Calendar month starts at 0

        return hasYearPassed(year) ||
                normalizeYear(year) == now.get(Calendar.YEAR) && month < (now.get(Calendar.MONTH) + 1);
    }

    // Convert two-digit year to full year if necessary
    protected int normalizeYear(int year)  {
        if (year < 100 && year >= 0) {
            Calendar now = Calendar.getInstance();
            String currentYear = String.valueOf(now.get(Calendar.YEAR));
            String prefix = currentYear.substring(0, currentYear.length() - 2);
            year = Integer.parseInt(String.format(Locale.US, "%s%02d", prefix, year));
        }
        return year;
    }
 }
