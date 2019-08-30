package com.hipay.fullservice.screen.fragment;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.fullservice.core.models.PaymentMethod;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.monitoring.CheckoutData;
import com.hipay.fullservice.core.monitoring.CheckoutDataNetwork;
import com.hipay.fullservice.core.monitoring.Monitoring;
import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.requests.payment.SepaDirectDebitPaymentMethodRequest;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IBANFormFragment extends AbstractPaymentFormFragment {

    private Button mPayButton;
    private FrameLayout mPayButtonLayout;

    protected TextInputEditText mFirstname;
    protected TextInputEditText mLastname;

    protected TextInputLayout mIbanLayout;
    protected TextInputEditText mIban;

    @Override
    public void launchRequest() {
        Bundle args = getArguments();

        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
        final PaymentProduct paymentProduct = PaymentProduct.fromBundle(args.getBundle(PaymentProduct.TAG));

        final String signature = args.getString(GatewayClient.SIGNATURE_TAG);

        SepaDirectDebitPaymentMethodRequest sddPaymentMethodRequest =
                new SepaDirectDebitPaymentMethodRequest(mFirstname.getText().toString(), mLastname.getText().toString(), mIban.getText().toString().toUpperCase());

        OrderRequest orderRequest = new OrderRequest(paymentPageRequest);
        orderRequest.setPaymentMethod(sddPaymentMethodRequest);

        orderRequest.setPaymentProductCode(paymentProduct.getCode());

        if (getActivity() != null) {

            mGatewayClient = new GatewayClient(getActivity());
            mCurrentLoading = AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue();

            final Date requestDate = new Date();

            mGatewayClient.requestNewOrder(orderRequest, signature, new OrderRequestCallback() {

                @Override
                public void onSuccess(final Transaction transaction) {

                    CheckoutData.checkoutData.setStatus(transaction.getStatus().getIntegerValue());
                    CheckoutData.checkoutData.setTransactionID(transaction.getTransactionReference());
                    CheckoutData.checkoutData.setEvent(CheckoutData.Event.request);

                    Monitoring monitoring = new Monitoring();
                    monitoring.setRequestDate(requestDate);
                    monitoring.setResponseDate(new Date());
                    CheckoutData.checkoutData.setMonitoring(monitoring);

                    AsyncTask<CheckoutData, Void, Integer> task = new CheckoutDataNetwork().execute(CheckoutData.checkoutData);
                    CheckoutData.checkoutData = null;

                    if (mCallback != null) {
                        cancelLoaderId(AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue());
                        mCallback.onCallbackOrderReceived(transaction, null);
                    }

                }

                @Override
                public void onError(Exception error) {

                    if (mCallback != null) {
                        cancelLoaderId(AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue());
                        mCallback.onCallbackOrderReceived(null, error);
                    }
                }
            });
        }
    }

    @Override
    protected boolean isInputDataValid() {

        if (isIBANValid(mIban.getText().toString().toUpperCase()) && isFirstnameValid() && isLastnameValid()) {
            return true;
        }

        return false;
    }

    @Override
    public void savePaymentMethod(PaymentMethod method) {

    }

    @Override
    public void setLoadingMode(boolean loadingMode, boolean delay) {

        if (!delay) {

            if (loadingMode) {

                mPayButtonLayout.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);

            } else {

                mPayButtonLayout.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);

            }
        }

        mLoadingMode = loadingMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.fragment_payment_sdd, container, false);
        return contentView;
    }

    @Override
    protected void initContentViews(View view) {
        super.initContentViews(view);

        mFirstname = view.findViewById(R.id.ssd_form_firstname);
        mFirstname.addTextChangedListener(new GenericTextWatcher(mFirstname));

        mLastname = view.findViewById(R.id.ssd_form_lastname);
        mLastname.addTextChangedListener(new GenericTextWatcher(mLastname));

        mIbanLayout = view.findViewById(R.id.ssd_form_iban_container);

        mIban = view.findViewById(R.id.ssd_form_iban);
        mIban.addTextChangedListener(new GenericTextWatcher(mIban));
        mIban.setFilters( new InputFilter[] { new InputFilter.AllCaps()});

        mPayButton = view.findViewById(R.id.pay_button);

        mPayButtonLayout = view.findViewById(R.id.pay_button_layout);
        mPayButtonLayout.setVisibility(View.VISIBLE);

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
            public void onClick(View v)
            {
                setLoadingMode(true,false);
                launchRequest();
            }
        });

        mFirstname.requestFocus();

        validatePayButton(false);
    }

    private class GenericTextWatcher implements TextWatcher {

        private View v;
        private InputFilter lastFilter;

        private GenericTextWatcher(View view) {
            this.v = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable editable) {

            int i = v.getId();
            String s = editable.toString();

            if (i == R.id.ssd_form_iban) {
                Integer maxIBANSize = getIBANMaxLength(s);

                if (s.length() > 4) {
                    if (maxIBANSize == -1) {
                        mIbanLayout.setError(getString(R.string.sdd_iban_invalid));
                    }
                    else if (isIBANCompleted(s)) {
                        if (isIBANValid(s)) {
                            mIbanLayout.setError(null);

                            //Hide Keyboard
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        }
                        else {
                            mIbanLayout.setError(getString(R.string.sdd_iban_invalid));
                        }
                    }
                    else {
                        mIbanLayout.setError(null);
                    }
                }
                else {
                    mIbanLayout.setError(null);
                }

                mIban.setFilters(new InputFilter[] { new InputFilter.AllCaps(), new InputFilter.LengthFilter((maxIBANSize == -1) ? 30 : maxIBANSize)});
            }

            validatePayButton(isInputDataValid());
        }
    }

    protected void validatePayButton(boolean validate) {

        if (validate) {

            final Bundle customThemeBundle = getArguments().getBundle(CustomTheme.TAG);
            CustomTheme theme = CustomTheme.fromBundle(customThemeBundle);

            mPayButton.setTextColor(ContextCompat.getColor(getActivity(), theme.getTextColorPrimaryId()));
            mPayButtonLayout.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mPayButtonLayout.setBackground(makeSelector(theme));

                Drawable[] drawables = mPayButton.getCompoundDrawables();
                Drawable wrapDrawable = DrawableCompat.wrap(drawables[0]);
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getActivity(), theme.getTextColorPrimaryId()));
            }

        } else {

            mPayButton.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
            mPayButtonLayout.setEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                CustomTheme greyTheme = new CustomTheme(R.color.dark_grey, R.color.dark_grey, R.color.dark_grey);
                mPayButtonLayout.setBackground(makeSelector(greyTheme));

                Drawable[] drawables = mPayButton.getCompoundDrawables();
                Drawable wrapDrawable = DrawableCompat.wrap(drawables[0]);
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getActivity(), android.R.color.white));
            }
        }
    }

    private StateListDrawable makeSelector(CustomTheme theme) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(ContextCompat.getColor(getActivity(), theme.getColorPrimaryDarkId())));
        res.addState(new int[]{}, new ColorDrawable(ContextCompat.getColor(getActivity(), theme.getColorPrimaryId())));
        return res;
    }


    private boolean isIBANValid(String iban) {

        if (!isIBANCompleted(iban)) {
            return false;
        }

        int ibanLength = iban.length();
        iban = iban.substring(4, ibanLength) + iban.substring(0,4);

        long total = 0;
        for (int i = 0; i < iban.length(); i++ ) {
            int numericValue = Character.getNumericValue(iban.charAt(i));

            total = (numericValue > 9 ? total * 100 : total * 10) + numericValue;

            if (total > 9999) {
                total = (total % 97);
            }
        }

        return (total % 97) == 1;
    }

    private boolean isIBANCompleted(String iban) {
        return iban.length() == getIBANMaxLength(iban);
    }

    private Integer getIBANMaxLength(String iban) {
        Map<String, Integer> codeLength = new HashMap<String, Integer>();

        codeLength.put("AD", 24);
        codeLength.put("AE", 23);
        codeLength.put("AT", 20);
        codeLength.put("AZ", 28);
        codeLength.put("BA", 20);
        codeLength.put("BE", 16);
        codeLength.put("BG", 22);
        codeLength.put("BH", 22);
        codeLength.put("BR", 29);
        codeLength.put("BY", 28);
        codeLength.put("CH", 21);
        codeLength.put("CR", 21);
        codeLength.put("CY", 28);
        codeLength.put("CZ", 24);
        codeLength.put("DE", 22);
        codeLength.put("DK", 18);
        codeLength.put("DO", 28);
        codeLength.put("EE", 20);
        codeLength.put("ES", 24);
        codeLength.put("FI", 18);
        codeLength.put("FO", 18);
        codeLength.put("FR", 27);
        codeLength.put("GB", 22);
        codeLength.put("GI", 23);
        codeLength.put("GL", 18);
        codeLength.put("GR", 27);
        codeLength.put("GT", 28);
        codeLength.put("HR", 21);
        codeLength.put("HU", 28);
        codeLength.put("IE", 22);
        codeLength.put("IL", 23);
        codeLength.put("IS", 26);
        codeLength.put("IT", 27);
        codeLength.put("JO", 30);
        codeLength.put("KW", 30);
        codeLength.put("KZ", 20);
        codeLength.put("LB", 28);
        codeLength.put("LI", 21);
        codeLength.put("LT", 20);
        codeLength.put("LU", 20);
        codeLength.put("LV", 21);
        codeLength.put("MC", 27);
        codeLength.put("MD", 24);
        codeLength.put("ME", 22);
        codeLength.put("MK", 19);
        codeLength.put("MR", 27);
        codeLength.put("MT", 31);
        codeLength.put("MU", 30);
        codeLength.put("NL", 18);
        codeLength.put("NO", 15);
        codeLength.put("PK", 24);
        codeLength.put("PL", 28);
        codeLength.put("PS", 29);
        codeLength.put("PT", 25);
        codeLength.put("QA", 29);
        codeLength.put("RO", 24);
        codeLength.put("RS", 22);
        codeLength.put("SA", 24);
        codeLength.put("SE", 24);
        codeLength.put("SI", 19);
        codeLength.put("SK", 24);
        codeLength.put("SM", 27);
        codeLength.put("TN", 24);
        codeLength.put("TR", 26);
        codeLength.put("VA", 22);
        codeLength.put("VG", 24);
        codeLength.put("XK", 20);


        if (iban.length() <= 2) {
            return -1;
        }

        String countryCode = iban.substring(0,2);
        Integer ibanLength = codeLength.get(countryCode);

        if (ibanLength == null) {
            return -1;
        }

        return ibanLength;
    }

    protected boolean isFirstnameValid() {

        if (!TextUtils.isEmpty(mFirstname.getText())) {

            String firstnameString = mFirstname.getText().toString().trim();

            if (firstnameString.length() > 1) {
                return true;
            }
        }
        return false;
    }

    protected boolean isLastnameValid() {

        if (!TextUtils.isEmpty(mLastname.getText())) {

            String lastnameString = mLastname.getText().toString().trim();

            if (lastnameString.length() > 1) {
                return true;
            }
        }
        return false;
    }
}
