package com.hipay.fullservice.screen.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.widget.SwitchCompat;
import android.text.Editable;
import android.text.InputFilter;
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
import com.hipay.fullservice.core.client.AbstractClient;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.client.SecureVaultClient;
import com.hipay.fullservice.core.client.config.ClientConfig;
import com.hipay.fullservice.core.client.interfaces.callbacks.OrderRequestCallback;
import com.hipay.fullservice.core.client.interfaces.callbacks.SecureVaultRequestCallback;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.models.PaymentMethod;
import com.hipay.fullservice.core.models.PaymentProduct;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.info.CustomerInfoRequest;
import com.hipay.fullservice.core.requests.order.OrderRequest;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.core.utils.NFCUtils;
import com.hipay.fullservice.core.utils.PaymentCardTokenDatabase;
import com.hipay.fullservice.core.utils.Utils;
import com.hipay.fullservice.screen.activity.PaymentFormActivity;
import com.hipay.fullservice.screen.fragment.interfaces.CardBehaviour;
import com.hipay.fullservice.screen.helper.FormHelper;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 * Created by HiPay on 20/04/16.
 */
public class TokenizableCardPaymentFormFragment extends AbstractPaymentFormFragment {

    private Button mScanButton;
    private Button mScanNfcButton;
    private LinearLayout mScanNfcInfoLayout;

    private Button mPayButton;
    private FrameLayout mPayButtonLayout;

    private PaymentProduct basicPaymentProduct;
    private String inferedPaymentProduct;

    private CardBehaviour mCardBehaviour;

    private SwitchCompat mCardStorageSwitch;
    private LinearLayout mCardStorageSwitchLayout;

    private PaymentCardToken mPaymentCardToken;

    private String mCardNumberCache;
    private String mMonthExpiryCache;
    private String mYearExpiryCache;
    private String mCardOwnerCache;
    private String mCardCVVCache;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //button to enable NFC on the device
        boolean isPaymentCardNfcScanButtonVisible = this.isPaymentCardNfcScanConfigEnabled() && this.isPaymentCardNfcScanAvailable() && !this.isPaymentCardNfcScanEnabled();
        mScanNfcButton.setVisibility(isPaymentCardNfcScanButtonVisible ? View.VISIBLE : View.GONE);

        boolean isPaymentCardNfcScanInfoVisible = this.isPaymentCardNfcScanConfigEnabled() && this.isPaymentCardNfcScanAvailable() && this.isPaymentCardNfcScanEnabled();
        mScanNfcInfoLayout.setVisibility(isPaymentCardNfcScanInfoVisible ? View.VISIBLE : View.GONE);

        if (isPaymentCardNfcScanInfoVisible) {
            NFCUtils.enableDispatch(getActivity());
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        NFCUtils.disableDispatch(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PaymentFormActivity.SCAN_REQUEST_CODE) {

            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                mCardNumber.setFilters( new InputFilter[] { new InputFilter.LengthFilter(Integer.MAX_VALUE)});
                mCardNumber.setText(scanResult.getFormattedCardNumber());

                if (scanResult.isExpiryValid()) {

                    String expiryYear = String.valueOf(scanResult.expiryYear);
                    if (expiryYear.length() == 4 && TextUtils.isDigitsOnly(expiryYear)) {

                        mCardExpiration.setText(scanResult.expiryMonth + "/" + expiryYear.substring(2,4));
                    }
                }
            }
            else {

                //no-op, scan was canceled.
            }
        }
    }

    @Override
    protected void initContentViews(View view) {
        super.initContentViews(view);

        mScanButton = (Button) view.findViewById(R.id.scan_button);
        mScanNfcButton = (Button) view.findViewById(R.id.scan_nfc_button);
        mScanNfcInfoLayout = (LinearLayout) view.findViewById(R.id.scan_nfc_info);

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
            public void onClick(View v)
            {
                setLoadingMode(true,false);
                launchRequest();
            }
        });

        mScanButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                askForScanPermission();
            }
        });

        boolean isPaymentCardScanButtonVisible = this.isPaymentCardScanConfigEnabled();
        mScanButton.setVisibility(isPaymentCardScanButtonVisible ? View.VISIBLE : View.GONE);

        mScanNfcButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                {
                    startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
                }
                else
                {
                    startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                }
            }
        });

        //button to enable NFC on the device
        boolean isPaymentCardNfcScanButtonVisible = this.isPaymentCardNfcScanConfigEnabled() && this.isPaymentCardNfcScanAvailable() && !this.isPaymentCardNfcScanEnabled();
        mScanNfcButton.setVisibility(isPaymentCardNfcScanButtonVisible ? View.VISIBLE : View.GONE);

        boolean isPaymentCardNfcScanInfoVisible = this.isPaymentCardNfcScanConfigEnabled() && this.isPaymentCardNfcScanAvailable() && this.isPaymentCardNfcScanEnabled();
        mScanNfcInfoLayout.setVisibility(isPaymentCardNfcScanInfoVisible ? View.VISIBLE : View.GONE);

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

        mCardStorageSwitch = (SwitchCompat) view.findViewById(R.id.card_storage_switch);
        mCardStorageSwitchLayout = (LinearLayout) view.findViewById(R.id.card_storage_layout);

        //switch visible or gone
        boolean isPaymentCardStorageSwitchVisible = this.isPaymentCardStorageConfigEnabled();
        mCardStorageSwitchLayout.setVisibility(isPaymentCardStorageSwitchVisible ? View.VISIBLE : View.GONE);

        if (inferedPaymentProduct == null) {
            inferedPaymentProduct = paymentProduct.getCode();
        }

        if (mCardBehaviour == null) {
            mCardBehaviour = new CardBehaviour(paymentProduct);
        }

        mCardBehaviour.updateForm(mCardNumber, mCardCVV, mCardExpiration, mCardCVVLayout, mSecurityCodeInfoTextview, mSecurityCodeInfoImageview, isPaymentCardStorageSwitchVisible ? mCardStorageSwitchLayout : null, false, getActivity());

        CustomerInfoRequest customerInfoRequest = paymentPageRequest.getCustomer();
        String displayName = customerInfoRequest.getDisplayName();
        if (displayName != null) {
            mCardOwner.setText(paymentPageRequest.getCustomer().getDisplayName());
        }

        mCardNumber.requestFocus();

        setElementsCache(true);

        validatePayButton(isInputDataValid());

        validateScanButton(true);
        validateNfcScanButton(true);

        putEverythingInRed();

    }

    private void askForScanPermission() {

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {

                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                };

                Snackbar.make(getView(), getString(R.string.scan_card_permission), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.settings), clickListener)
                        .setActionTextColor(Color.YELLOW)
                        .show();

            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        PaymentFormActivity.SCAN_PERMISSION_REQUEST_CODE);
            }

        } else {

            launchScanCard();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PaymentFormActivity.SCAN_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    launchScanCard();
                }
        }
    }

    private void launchScanCard() {

        Intent scanIntent = new Intent(getActivity(), CardIOActivity.class);

        //scanIntent.putExtra(CardIOActivity.EXTRA_NO_CAMERA, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false);
        scanIntent.putExtra(CardIOActivity.EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY, false);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, false);

        // hides the manual entry button
        // if set, developers should provide their own manual entry mechanism in the app
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true);

        // matches the theme of your application
        scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, true);

        scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, false);
        scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, false);
        scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false);
        scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_OVERLAY_LAYOUT_ID, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false);

        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, true);

        // SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        ActivityCompat.startActivityForResult(getActivity(), scanIntent, PaymentFormActivity.SCAN_REQUEST_CODE, null);
    }

    @Override
    public void setLoadingMode(boolean loadingMode, boolean delay) {

        setElementsCache(loadingMode);

        if (!delay) {

            if (loadingMode) {

                mPayButtonLayout.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);

                mCardOwner.setEnabled(false);
                mCardCVV.setEnabled(false);
                mCardExpiration.setEnabled(false);
                mCardNumber.setEnabled(false);
                mCardStorageSwitch.setEnabled(false);

            } else {

                mPayButtonLayout.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);

                mCardOwner.setEnabled(true);
                mCardCVV.setEnabled(true);
                mCardExpiration.setEnabled(true);
                mCardNumber.setEnabled(true);
                mCardStorageSwitch.setEnabled(true);

            }

            validateScanButton(!loadingMode);
            validateNfcScanButton(!loadingMode);
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

    public void fillCardNumber(String cardNumberText, Date expiryDate) {

        mCardNumber.setFilters( new InputFilter[] { new InputFilter.LengthFilter(Integer.MAX_VALUE)});
        mCardNumber.setText(Utils.formatCardNumber(cardNumberText));

        if (expiryDate != null) {
            mCardExpiration.setText(Utils.getPaymentFormStringFromDate(expiryDate));
        }
    }

    protected void validateScanButton(boolean validate) {

        if (validate) {
            mScanButton.setEnabled(true);

        } else {
            mScanButton.setEnabled(false);
        }
    }

    protected void validateNfcScanButton(boolean validate) {

        if (validate) {
            mScanNfcButton.setEnabled(true);

        } else {
            mScanNfcButton.setEnabled(false);
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

                    if (version.length() == 1 && diffLength == 1 && changeStart == 0) {

                        if (TextUtils.isDigitsOnly(version) && Integer.valueOf(version) > 1)
                        {
                            editable.insert(0, "0");
                        }
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
                    if (diffLength == -1 && isLastSpace == true && editable.length() > 0) {

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

                } else if (!isInferedOrCardDomesticNetwork(inferedPaymentProduct) &&
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

    private void setElementsCache(boolean bool) {

        if (bool) {

            if (mCardNumberCache == null) {

                mCardNumberCache = mCardNumber.getText().toString().replaceAll(" ", "");
            }

            if (mMonthExpiryCache == null) {
                mMonthExpiryCache = this.getMonthFromExpiry(mCardExpiration.getText().toString());
            }

            if (mYearExpiryCache == null) {
                mYearExpiryCache = this.getYearFromExpiry(mCardExpiration.getText().toString());
            }

            if (mCardCVVCache == null) {
                mCardCVVCache = mCardCVV.getText().toString();
            }

            if (mCardOwnerCache == null) {
                mCardOwnerCache = mCardOwner.getText().toString();
            }

        } else {

            mCardNumberCache = null;
            mMonthExpiryCache = null;
            mYearExpiryCache = null;
            mCardCVVCache = null;
            mCardOwnerCache = null;
        }
    }

    @Override
    public void launchRequest() {

        Bundle args = getArguments();

        final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
        final PaymentProduct paymentProduct = PaymentProduct.fromBundle(args.getBundle(PaymentProduct.TAG));

        final String signature = args.getString(GatewayClient.SIGNATURE_TAG);

        if (this.isPaymentCardStorageConfigEnabled() && this.isPaymentCardStorageEnabled() && mCardStorageSwitch.isChecked())
        {
            paymentPageRequest.setMultiUse(true);
        }

        mSecureVaultClient = new SecureVaultClient(getActivity());
        mCurrentLoading = AbstractClient.RequestLoaderId.GenerateTokenReqLoaderId.getIntegerValue();

        setElementsCache(true);

        mSecureVaultClient.generateToken(

                mCardNumberCache,
                mMonthExpiryCache,
                mYearExpiryCache,
                mCardOwnerCache,
                mCardCVVCache,
                paymentPageRequest.getMultiUse(),

                new SecureVaultRequestCallback() {
                    @Override
                    public void onSuccess(PaymentCardToken paymentCardToken) {

                        mPaymentCardToken = paymentCardToken;

                        //secure vault
                        cancelLoaderId(AbstractClient.RequestLoaderId.GenerateTokenReqLoaderId.getIntegerValue());

                        OrderRequest orderRequest = new OrderRequest(paymentPageRequest);

                        String productCode = paymentCardToken.getDomesticNetwork() != null ? paymentCardToken.getDomesticNetwork() : paymentCardToken.getBrand();
                        productCode = productCode.replace(" ", "-"); // Product code contains no spaces

                        orderRequest.setPaymentProductCode(productCode);

                        CardTokenPaymentMethodRequest cardTokenPaymentMethodRequest =
                                new CardTokenPaymentMethodRequest(
                                        mPaymentCardToken.getToken(),
                                        paymentPageRequest.getEci(),
                                        paymentPageRequest.getAuthenticationIndicator());

                        orderRequest.setPaymentMethod(cardTokenPaymentMethodRequest);

                        //check if activity is still available
                        if (getActivity() != null) {

                            mGatewayClient = new GatewayClient(getActivity());
                            mCurrentLoading = AbstractClient.RequestLoaderId.OrderReqLoaderId.getIntegerValue();

                            final Date requestDate = new Date();

                            mGatewayClient.requestNewOrder(orderRequest, signature, new OrderRequestCallback() {

                                @Override
                                public void onSuccess(final Transaction transaction) {

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
                    public void onError(Exception error) {

                        if (mCallback != null) {
                            cancelLoaderId(AbstractClient.RequestLoaderId.GenerateTokenReqLoaderId.getIntegerValue());
                            mCallback.onCallbackOrderReceived(null, error);
                        }
                    }
                }
        );
    }

    @Override
    public void savePaymentMethod(PaymentMethod paymentMethod) {

        if (paymentMethod instanceof PaymentCardToken) {
            PaymentCardToken paymentCardToken = (PaymentCardToken)paymentMethod;

            if (mPaymentCardToken != null && mPaymentCardToken.equals(paymentCardToken)) {

                if (this.isPaymentCardStorageConfigEnabled() && this.isPaymentCardStorageEnabled() && mCardStorageSwitch.isChecked()) {

                    Bundle args = getArguments();

                    final PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
                    PaymentCardTokenDatabase.getInstance().save(getActivity(), paymentCardToken, paymentPageRequest.getCurrency());
                }
            }
        }
    }

    private String getMonthFromExpiry(String expiryString) {

        if (expiryString != null && !expiryString.isEmpty()) {
            return expiryString.substring(0,2);
        }

        return null;
    }

    private String getYearFromExpiry(String expiryString) {

        if (expiryString != null && !expiryString.isEmpty()) {

            String year = null;
            if (expiryString.length() == 5 && expiryString.charAt(2) == '/') {
                year = expiryString.substring(3,5);

            } else if (expiryString.length() == 4 && TextUtils.isDigitsOnly(expiryString)) {
                year = expiryString.substring(2,4);
            }

            if (year != null) {
                return String.valueOf(FormHelper.normalizeYear(Integer.valueOf(year)));
            }
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

        String cardNumber = mCardNumber.getText().toString();
        if (!TextUtils.isEmpty(cardNumber)) {

            //luhn first
            if (!FormHelper.luhnTest(cardNumber.replaceAll(" ", ""))) {
                return false;
            }

            //format then
            Set<String> paymentProducts = FormHelper.getPaymentProductCodes(cardNumber, getActivity());
            if (paymentProducts.isEmpty()) {
                return false;
            }

            if (paymentProducts.size() == 1) {

                String[] things = paymentProducts.toArray(new String[1]);

                if (FormHelper.hasValidCardLength(cardNumber, things[0], getActivity())) {

                    return true;
                }
            }
        }

        return false;
    }

    private boolean isPaymentCardScanConfigEnabled()
    {
        return ClientConfig.getInstance().isPaymentCardScanEnabled() && CardIOActivity.canReadCardWithCamera();

    }

    private boolean isPaymentCardNfcScanConfigEnabled()
    {
        return ClientConfig.getInstance().isPaymentCardNfcScanEnabled();
    }

    private boolean isPaymentCardNfcScanEnabled()
    {
        return NFCUtils.isNfcEnabled(getActivity());
    }

    private boolean isPaymentCardNfcScanAvailable()
    {
        return NFCUtils.isNfcAvailable(getActivity());
    }

    private boolean isPaymentCardStorageConfigEnabled()
    {
        boolean paymentCardEnabled = ClientConfig.getInstance().isPaymentCardStorageEnabled();

        Bundle args = getArguments();
        PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(args.getBundle(PaymentPageRequest.TAG));
        boolean paymentPageRequestECI = paymentPageRequest.getEci() == Transaction.ECI.SecureECommerce ? true : false;

        return paymentCardEnabled && paymentPageRequestECI;
    }

    private boolean isPaymentCardStorageEnabled()
    {
        String paymentProduct;

        if (inferedPaymentProduct != null) {
            paymentProduct = inferedPaymentProduct;
        } else {
            paymentProduct = mCardBehaviour.getProductCode();
        }

        if (paymentProduct.equals(PaymentProduct.PaymentProductCodeBCMC) || paymentProduct.equals(PaymentProduct.PaymentProductCodeMaestro)) {
            return false;
        }

        return true;
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

                    // we do pass switchLayout as a parameter if config is enabled
                    LinearLayout switchLayout = this.isPaymentCardStorageConfigEnabled() ? mCardStorageSwitchLayout : null;

                    mCallback.updatePaymentProduct(inferedPaymentProduct);
                    mCardBehaviour.updatePaymentProduct(inferedPaymentProduct);
                    mCardBehaviour.updateForm(mCardNumber, mCardCVV, mCardExpiration, mCardCVVLayout, mSecurityCodeInfoTextview, mSecurityCodeInfoImageview, switchLayout, false, getActivity());

                    this.putEverythingInRed();
                }
            } else {
                //textfield is empty
                mCardStorageSwitch.setChecked(false);
            }

        }

        this.mCardNumber.setTextColor(ContextCompat.getColor(getActivity(), color));
    }

    private boolean isInferedOrCardDomesticNetwork(String product) {

        return product.equals(PaymentProduct.PaymentProductCodeCB) ||
                product.equals(PaymentProduct.PaymentProductCategoryCodeCard);
    }

}

