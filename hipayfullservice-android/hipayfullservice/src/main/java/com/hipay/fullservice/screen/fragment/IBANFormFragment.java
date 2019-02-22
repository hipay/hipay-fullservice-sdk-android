package com.hipay.fullservice.screen.fragment;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.fullservice.core.models.PaymentMethod;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.requests.payment.SepaDirectDebitPaymentMethodRequest;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IBANFormFragment extends AbstractPaymentFormFragment {

    private Button mPayButton;
    private FrameLayout mPayButtonLayout;

    protected TextInputEditText mIban;

    @Override
    public void launchRequest() {
        Bundle args = getArguments();

        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
        final PaymentProduct paymentProduct = PaymentProduct.fromBundle(args.getBundle(PaymentProduct.TAG));

        final String signature = args.getString(GatewayClient.SIGNATURE_TAG);

        SepaDirectDebitPaymentMethodRequest sddPaymentMethodRequest =
                new SepaDirectDebitPaymentMethodRequest("John", "doe", "FR7630006000011234567890189", 1);

        OrderRequest orderRequest = new OrderRequest(paymentPageRequest);
        orderRequest.setPaymentMethod(sddPaymentMethodRequest);

        orderRequest.setPaymentProductCode(paymentProduct.getCode());

        //check if activity is still available
        if (getActivity() != null) {

            mGatewayClient = new GatewayClient(getActivity());
            mCurrentLoading = AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue();

            mGatewayClient.requestNewOrder(orderRequest, signature, new OrderRequestCallback() {

                @Override
                public void onSuccess(final Transaction transaction) {
                    Log.i("transaction success", transaction.toString());

                    if (mCallback != null) {
                        cancelLoaderId(AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue());
                        mCallback.onCallbackOrderReceived(transaction, null);
                    }

                }

                @Override
                public void onError(Exception error) {
                    Log.i("transaction failed", error.getLocalizedMessage());

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
        System.out.println(ibanValidation("CH10002300A1023502601"));
        System.out.println(ibanValidation("AT483200000012345864"));
        System.out.println(ibanValidation("BG18RZBB91550123456781")); // false
        System.out.println(ibanValidation("DK9520000123456711")); // false
        System.out.println(ibanValidation("GR9608100010000001234567890"));
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
    protected void initContentViews(View view) {
        super.initContentViews(view);

        mIban = view.findViewById(R.id.ssd_form_iban);

        mIban.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.print("beforeTextChanged");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.print("onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.print("afterTextChanged");

                boolean valid = !ibanCompleted(s.toString()) || (ibanValidation(s.toString()) && ibanCompleted(s.toString()));
                mIban.setTextColor(ContextCompat.getColor(getActivity(), valid ? R.color.hpf_accent : R.color.hpf_error));

                Integer ibanSize = ibanMaxLength(s.toString());
                mIban.setFilters( new InputFilter[] { new InputFilter.LengthFilter((ibanSize == -1) ? 30 : ibanSize)});
            }
        });

        mPayButton = (Button) view.findViewById(R.id.pay_button);

        mPayButtonLayout = (FrameLayout) view.findViewById(R.id.pay_button_layout);
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



        validatePayButton(true);
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


    private boolean ibanValidation(String iban) {

        if (ibanCompleted(iban)) {
            return false;
        }

        //CH10002300A1023502601
        String subIban = iban.substring(2,4);

        int checkDigit = Integer.parseInt(subIban);
        iban = iban.substring(4, iban.length()) + iban.substring(0,4);

        //002300A1023502601CH10
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

    private boolean ibanCompleted(String iban) {
        return iban.length() == ibanMaxLength(iban);
    }

    private Integer ibanMaxLength(String iban) {
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

        if (ibanLength == null || ibanLength != iban.length()) {
            return -1;
        }

        return ibanLength;
    }
}
