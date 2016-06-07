package com.hipay.hipayfullservice.screen.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

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
import com.hipay.hipayfullservice.screen.fragment.interfaces.CardBehaviour;
import com.hipay.hipayfullservice.screen.helper.FormHelper;

import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

/**
 * Created by nfillion on 20/04/16.
 */
public class TokenizableCardPaymentFormFragment extends AbstractPaymentFormFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        putEverythingInRed();
    }

    @Override
    protected void initContentViews(View view) {
        super.initContentViews(view);

        mCardInfoLayout.setVisibility(View.VISIBLE);


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

        Bundle args = getArguments();
        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));

        // check every product code to know how to handle the editTexts
        PaymentProduct paymentProduct = PaymentProduct.fromBundle(args.getBundle(PaymentProduct.TAG));
        inferedPaymentProduct = paymentProduct.getCode();
        mCardBehaviour = new CardBehaviour(paymentProduct);
        mCardBehaviour.updateForm(mCardNumber, mCardCVV, mCardExpiration, mCardCVVLayout, getActivity());

        CustomerInfoRequest customerInfoRequest = paymentPageRequest.getCustomer();
        String displayName = customerInfoRequest.getDisplayName();
        if (displayName != null) {
            mCardOwner.setText(paymentPageRequest.getCustomer().getDisplayName());
        }

        mCardNumber.requestFocus();

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

            //every time the user types
            if (!TextUtils.isEmpty(mCardNumber.getText()) && inferedPaymentProduct != null) {

                Set<String> paymentProductCodes = FormHelper.getPaymentProductCodes(mCardNumber.getText().toString(), getActivity());
                if (paymentProductCodes.size() == 1 && !paymentProductCodes.contains(inferedPaymentProduct)) {

                    String[] things = paymentProductCodes.toArray(new String[1]);
                    inferedPaymentProduct = things[0];
                    mCallback.updatePaymentProduct(inferedPaymentProduct);

                    mCardBehaviour.updatePaymentProduct(inferedPaymentProduct);
                    mCardBehaviour.updateForm(mCardNumber, mCardCVV, mCardExpiration, mCardCVVLayout, getActivity());

                    this.putEverythingInRed();
                }
            }

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

