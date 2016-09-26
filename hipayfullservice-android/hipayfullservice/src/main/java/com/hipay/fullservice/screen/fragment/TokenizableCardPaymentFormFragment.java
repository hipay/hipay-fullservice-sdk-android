package com.hipay.fullservice.screen.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.client.SecureVaultClient;
import com.hipay.fullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.screen.fragment.interfaces.CardBehaviour;
import com.hipay.fullservice.screen.helper.FormHelper;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;

/**
 * Created by nfillion on 20/04/16.
 */
public class TokenizableCardPaymentFormFragment extends AbstractPaymentFormFragment {

    private Button mPayButton;
    private FrameLayout mPayButtonLayout;

    private PaymentProduct basicPaymentProduct;
    private String inferedPaymentProduct;

    private CardBehaviour mCardBehaviour;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        validatePayButton(isInputDataValid());
        putEverythingInRed();
    }

    @Override
    protected void initContentViews(View view) {
        super.initContentViews(view);

        mPayButton = (Button) view.findViewById(R.id.pay_button);
        mPayButtonLayout = (FrameLayout) view.findViewById(R.id.pay_button_layout);

        mPayButtonLayout.setVisibility(View.VISIBLE);

        mCardInfoLayout.setVisibility(View.VISIBLE);

        mSecurityCodeInfoLayout = (LinearLayout) view.findViewById(R.id.card_cvv_info);
        mSecurityCodeInfoTextview = (TextView) view.findViewById(R.id.card_cvv_info_text);
        mSecurityCodeInfoImageview = (ImageView) view.findViewById(R.id.card_cvv_info_src);

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

                setLoadingMode(true,false);
                launchRequest();
            }
        });
        View.OnFocusChangeListener focusChangeListener = this.focusChangeListener();


        mCardOwner = (TextInputEditText) view.findViewById(R.id.card_owner);

        mCardOwner.setOnFocusChangeListener(focusChangeListener);
        mCardOwner.addTextChangedListener(new GenericTextWatcher(mCardOwner));

        mCardNumber = (TextInputEditText) view.findViewById(R.id.card_number);
        mCardNumber.addTextChangedListener(new GenericTextWatcher(mCardNumber));
        mCardNumber.setOnFocusChangeListener(focusChangeListener);

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

        // check every product code to know how to handle the editTexts
        PaymentProduct paymentProduct = PaymentProduct.fromBundle(args.getBundle(PaymentProduct.TAG));

        basicPaymentProduct = paymentProduct;
        inferedPaymentProduct = paymentProduct.getCode();

        mCardBehaviour = new CardBehaviour(paymentProduct);
        mCardBehaviour.updateForm(mCardNumber, mCardCVV, mCardExpiration, mCardCVVLayout, mSecurityCodeInfoTextview, mSecurityCodeInfoImageview, false, getActivity());

        CustomerInfoRequest customerInfoRequest = paymentPageRequest.getCustomer();
        String displayName = customerInfoRequest.getDisplayName();
        if (displayName != null) {
            mCardOwner.setText(paymentPageRequest.getCustomer().getDisplayName());
        }

        mCardNumber.requestFocus();

    }

    @Override
    public void setLoadingMode(boolean loadingMode, boolean delay) {

        if (!delay) {

            if (loadingMode) {

                mPayButtonLayout.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);

                mCardOwner.setEnabled(false);
                mCardCVV.setEnabled(false);
                mCardExpiration.setEnabled(false);
                mCardNumber.setEnabled(false);

            } else {

                mPayButtonLayout.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);

                mCardOwner.setEnabled(true);
                mCardCVV.setEnabled(true);
                mCardExpiration.setEnabled(true);
                mCardNumber.setEnabled(true);
            }
        }

        mLoadingMode = loadingMode;
    }

    private View.OnFocusChangeListener focusChangeListener() {

        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                int i = v.getId();

                if (i == R.id.card_owner) {

                    putCardOwnerInRed(hasFocus);

                } else if (i == R.id.card_cvv) {

                    putCVVInRed(!hasFocus);

                    mSecurityCodeInfoLayout.setVisibility(hasFocus? View.VISIBLE : View.GONE);

                } else if (i == R.id.card_expiration) {

                    putExpiryDateInRed(!hasFocus);

                } else if (i == R.id.card_number) {

                    putCardNumberInRed(!hasFocus);

                } else {
                    throw new UnsupportedOperationException(
                            "onFocusChange has not been implemented for " + getResources().
                                    getResourceName(v.getId()));
                }
            }
        };
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

    private StateListDrawable makeSelector(CustomTheme theme) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(ContextCompat.getColor(getActivity(), theme.getColorPrimaryDarkId())));
        res.addState(new int[]{}, new ColorDrawable(ContextCompat.getColor(getActivity(), theme.getColorPrimaryId())));
        return res;
    }

    private class GenericTextWatcher implements TextWatcher {

        private int diffLength = 0;
        private int changeStart = 0;
        private boolean isLastSpace = false;
        private int currentLength = 0;

        private View v;
        private GenericTextWatcher(View view) {
            this.v = view;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            diffLength = after - count;
            changeStart = start;

            if (s.length() > 0 && s.charAt(s.length()-1) == ' ') {
                isLastSpace = true;
            } else {
                isLastSpace = false;
            }
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            diffLength = count - before;
            changeStart = start;

            currentLength = s.length();
        }

        public void afterTextChanged(Editable editable) {

            int i = v.getId();

            String version = editable.toString();

            if (i == R.id.card_owner) {

                putCardOwnerInRed(false);

            } else if (i == R.id.card_cvv) {

                // the condition to say it is wrong
                putCVVInRed(false);
                if (isCVVValid()) {

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mCardCVV.getWindowToken(), 0);
                }

            } else if (i == R.id.card_expiration) {

                //TODO handle editext expiration
                //check how to handle the add or suppression in the middle of the string
                //check how to handle a copy paste (remove x chars, add y chars...)

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

                if (isExpiryDateValid()) {

                    if (hasSecurityCode()) {

                        mCardCVV.requestFocus();

                    } else {

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mCardExpiration.getWindowToken(), 0);
                    }
                }

            } else if (i == R.id.card_number) {

                //user deletes data

                if (diffLength < 0) {

                    // delete the space when we find one
                    if (diffLength == -1 && isLastSpace == true) {
                        editable.delete(editable.length()-1, editable.length());
                    }

                } else {

                    if (diffLength == 1 && changeStart == currentLength-1) {
                        if (hasSpaceAtIndex(currentLength)) {
                            editable.insert(currentLength, " ");
                        }
                    }

                }
                putCardNumberInRed(false);

                if (isCardNumberValid()) {

                    mCardExpiration.requestFocus();

                } else if ( !isInferedOrCardDomesticNetwork(inferedPaymentProduct) &&
                        FormHelper.hasValidCardLength(mCardNumber.getText().toString(), inferedPaymentProduct, getActivity())) {

                    putCardNumberInRed(true);

                } else {

                    putCardNumberInRed(false);
                }

            } else {
                throw new UnsupportedOperationException(
                        "OnClick has not been implemented for " + getResources().
                                getResourceName(v.getId()));
            }
            validatePayButton(isInputDataValid());
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

        final String signature = args.getString(GatewayClient.SIGNATURE_TAG);

        mSecureVaultClient = new SecureVaultClient(getActivity());
        mCurrentLoading = 0;
        mSecureVaultClient.generateToken(

                mCardNumber.getText().toString(),

                this.getMonthFromExpiry(mCardExpiration.getText().toString()),
                this.getYearFromExpiry(mCardExpiration.getText().toString()),
                mCardOwner.getText().toString(),
                mCardCVV.getText().toString(),
                false,

                new SecureVaultRequestCallback() {
                    @Override
                    public void onSuccess(PaymentCardToken paymentCardToken) {

                        Log.i(paymentCardToken.toString(), paymentCardToken.toString());

                        OrderRequest orderRequest = new OrderRequest(paymentPageRequest);

                        String productCode = paymentProduct.getCode();
                        if (productCode.equals(PaymentProduct.PaymentProductCategoryCodeCard)) {
                            productCode = mCardBehaviour.getProductCode();
                        }

                        orderRequest.setPaymentProductCode(productCode);

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


                            mGatewayClient.requestNewOrder(orderRequest, signature, new OrderRequestCallback() {

                                @Override
                                public void onSuccess(final Transaction transaction) {
                                    //Log.i("transaction success", transaction.toString());

                                    if (mCallback != null) {
                                        mCallback.onCallbackOrderReceived(transaction, null);
                                    }

                                    cancelLoaderId(1);
                                }

                                @Override
                                public void onError(Exception error) {
                                    //Log.i("transaction failed", error.getLocalizedMessage());
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
            return String.valueOf(FormHelper.normalizeYear(Integer.valueOf(year)));
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

    protected boolean isCVVValid() {

        return mCardBehaviour.isSecurityCodeValid(mCardCVV);
    }

    protected boolean hasSecurityCode() {

        return mCardBehaviour.hasSecurityCode();
    }

    protected boolean isCardOwnerValid() {

        if (!TextUtils.isEmpty(mCardOwner.getText())) {
            return true;
        }
        return false;
    }

    protected boolean hasSpaceAtIndex(int index) {

        return mCardBehaviour.hasSpaceAtIndex(index, getActivity());
    }

    protected boolean isCardNumberValid() {

        if (!TextUtils.isEmpty(mCardNumber.getText())) {

            //luhn first
            if (!FormHelper.luhnTest(mCardNumber.getText().toString().replaceAll(" ", ""))) {
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
                    return FormHelper.validateExpiryDate(expiryDate);
                }

            } else if (expiryDateString.length() == 4 && TextUtils.isDigitsOnly(expiryDateString)) {

                return FormHelper.validateExpiryDate(expiryDateString);
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

        boolean expiryDateValid = this.isExpiryDateValid();
        if (red && !expiryDateValid) {
            color = R.color.hpf_error;
        } else {
            color = R.color.hpf_accent;
        }

        this.mCardExpiration.setTextColor(ContextCompat.getColor(getActivity(), color));
    }
    private void putCVVInRed(boolean red) {

        int color;

        boolean securityCodeValid = this.isCVVValid();

        if (red && !securityCodeValid) {
            color = R.color.hpf_error;

        } else {
            color = R.color.hpf_accent;
        }

        this.mCardCVV.setTextColor(ContextCompat.getColor(getActivity(), color));
    }
    private void putCardOwnerInRed(boolean red) {

        int color;

        if (red && !this.isCardOwnerValid()) {
            color = R.color.hpf_error;

        } else {
            color = R.color.hpf_accent;
        }

        this.mCardOwner.setTextColor(ContextCompat.getColor(getActivity(), color));
    }

    private void putCardNumberInRed(boolean red) {

        int color;

        if (red && !this.isCardNumberValid()) {
            color = R.color.hpf_error;

        } else {

            color = R.color.hpf_accent;

            //every time the user types
            if (!TextUtils.isEmpty(mCardNumber.getText()) && inferedPaymentProduct != null) {

                Set<String> paymentProductCodes = FormHelper.getPaymentProductCodes(mCardNumber.getText().toString(), getActivity());
                if (paymentProductCodes.size() == 1 && !paymentProductCodes.contains(inferedPaymentProduct)) {

                    String[] things = paymentProductCodes.toArray(new String[1]);

                    inferedPaymentProduct = things[0];

                    if (isDomesticNetwork(inferedPaymentProduct)) {

                        //on garde le inferedPaymentProduct (VISA) mais on met l'image et titre de CB
                        mCallback.updatePaymentProduct(basicPaymentProduct.getPaymentProductDescription());
                        mCardBehaviour.updatePaymentProduct(inferedPaymentProduct);
                        mCardBehaviour.updateForm(mCardNumber, mCardCVV, mCardExpiration, mCardCVVLayout, mSecurityCodeInfoTextview, mSecurityCodeInfoImageview, false, getActivity());

                    } else {

                        mCallback.updatePaymentProduct(inferedPaymentProduct);
                        mCardBehaviour.updatePaymentProduct(inferedPaymentProduct);
                        mCardBehaviour.updateForm(mCardNumber, mCardCVV, mCardExpiration, mCardCVVLayout, mSecurityCodeInfoTextview, mSecurityCodeInfoImageview, false, getActivity());
                    }

                    this.putEverythingInRed();

                }

                // on va essayer d'atteindre la taille max.

            }

        }

        this.mCardNumber.setTextColor(ContextCompat.getColor(getActivity(), color));
    }

    /*
    private void backtoOrigin() {

        mCallback.updatePaymentProduct(basicPaymentProduct.getPaymentProductDescription());

        mCardBehaviour.updatePaymentProduct(basicPaymentProduct.getCode());
        mCardBehaviour.updateForm(mCardNumber, mCardCVV, mCardExpiration, mCardCVVLayout, getActivity());

        inferedPaymentProduct = basicPaymentProduct.getCode();
    }
    */

    private boolean isInferedOrCardDomesticNetwork(String product) {

        return product.equals(PaymentProduct.PaymentProductCodeCB) ||
                product.equals(PaymentProduct.PaymentProductCodeBCMC) ||
                product.equals(PaymentProduct.PaymentProductCategoryCodeCard);
    }

    private boolean isDomesticNetwork() {

        return basicPaymentProduct.getCode().equals(PaymentProduct.PaymentProductCodeCB) ||
                basicPaymentProduct.getCode().equals(PaymentProduct.PaymentProductCodeBCMC);
    }

    private boolean isDomesticNetwork(String paymentProductCode) {

        String basicProductCode = basicPaymentProduct.getCode();

        if (isDomesticNetwork()) {

            if (basicProductCode.equals(PaymentProduct.PaymentProductCodeBCMC)) {

                return paymentProductCode.equals(PaymentProduct.PaymentProductCodeMaestro);

            } else if (basicProductCode.equals(PaymentProduct.PaymentProductCodeCB)) {

                return
                        paymentProductCode.equals(PaymentProduct.PaymentProductCodeVisa) ||
                                paymentProductCode.equals(PaymentProduct.PaymentProductCodeMasterCard);
            }
        }

        return false;
    }
}

