package com.hipay.fullservice.example.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hipay.fullservice.core.client.config.ClientConfig;
import com.hipay.fullservice.core.errors.Errors;
import com.hipay.fullservice.core.errors.exceptions.ApiException;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.fullservice.example.DemoActivity;
import com.hipay.fullservice.example.Preferences;
import com.hipay.fullservice.example.R;
import com.hipay.fullservice.screen.activity.PaymentScreenActivity;
import com.hipay.fullservice.screen.helper.ApiLevelHelper;
import com.hipay.fullservice.screen.model.CustomTheme;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Created by HiPay on 15/03/16.
 */

public class DemoFragment extends Fragment {

    private static final String STATE_IS_LOADING = "isLoading";
    private static final String TAG = "SignatureTAG";

    RequestQueue mRequestQueue;

    private FloatingActionButton mDoneFab;
    private EditText mAmount;
    private CustomTheme customTheme;

    private ProgressBar mProgressBar;

    private AppCompatButton mEnvironmentChoice;

    private SwitchCompat mGroupCardSwitch;
    private SwitchCompat mCardStorageSwitch;
    private SwitchCompat mCardScanSwitch;
    private SwitchCompat mCardNfcScanSwitch;

    private AppCompatSpinner mCurrencySpinner;
    private AppCompatSpinner m3DSSpinner;

    private AppCompatButton mPaymentProductsButton;
    protected boolean inhibit_spinner;

    private EditText mTimeout;

    protected boolean mLoadingMode;

    public static DemoFragment newInstance() {

        DemoFragment fragment = new DemoFragment();
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PaymentPageRequest.REQUEST_ORDER) {

            if (resultCode == R.id.transaction_succeed) {

                Bundle transactionBundle = data.getBundleExtra(Transaction.TAG);
                Transaction transaction = Transaction.fromBundle(transactionBundle);

                Snackbar snackbar = Snackbar.make(mDoneFab, "Transaction state: " + transaction.getState().getStringValue(),
                        Snackbar.LENGTH_INDEFINITE);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor((ContextCompat.getColor(getActivity(),
                        android.R.color.holo_green_light)));
                snackbar.show();

            } else if (resultCode == R.id.transaction_failed) {

                Bundle exceptionBundle = data.getBundleExtra(Errors.TAG);
                ApiException exception = ApiException.fromBundle(exceptionBundle);

                Snackbar snackbar = Snackbar.make(mDoneFab, getString(R.string.payment_error, exception.getLocalizedMessage()),
                        Snackbar.LENGTH_INDEFINITE);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor((ContextCompat.getColor(getActivity(),
                        android.R.color.holo_red_light)));
                snackbar.show();
            } else if (resultCode == R.id.transaction_timeout) {

                Bundle bundle = data.getExtras();
                String descriptionError = bundle.getString(Errors.TAG);

                Snackbar snackbar = Snackbar.make(mDoneFab, getString(R.string.payment_error, descriptionError),
                        Snackbar.LENGTH_INDEFINITE);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor((ContextCompat.getColor(getActivity(),
                        android.R.color.holo_red_light)));
                snackbar.show();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        CustomTheme theme;

        if (savedInstanceState == null) {
            theme = new CustomTheme(
                    R.color.hpf_primary,
                    R.color.hpf_primary_dark,
                    R.color.theme_blue_text);

        } else {

            Bundle themeBundle = savedInstanceState.getBundle(CustomTheme.TAG);
            theme = CustomTheme.fromBundle(themeBundle);

            mLoadingMode = savedInstanceState.getBoolean(STATE_IS_LOADING);
        }

        inhibit_spinner = true;

        this.setCustomTheme(theme);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View contentView = inflater.inflate(R.layout.fragment_demo, container, false);

        mEnvironmentChoice = contentView.findViewById(R.id.payment_products_environment);
        mEnvironmentChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnEnvironment();
            }
        });

        mGroupCardSwitch = (SwitchCompat) contentView.findViewById(R.id.group_card_switch);
        mCardStorageSwitch = (SwitchCompat) contentView.findViewById(R.id.card_storage_switch);
        mCardScanSwitch = (SwitchCompat) contentView.findViewById(R.id.card_scan_switch);
        mCardNfcScanSwitch = (SwitchCompat) contentView.findViewById(R.id.card_nfc_scan_switch);

        mGroupCardSwitch.setTextColor(ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));
        mCardStorageSwitch.setTextColor(ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));
        mCardScanSwitch.setTextColor(ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));
        mCardNfcScanSwitch.setTextColor(ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));

        mCurrencySpinner = (AppCompatSpinner) contentView.findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCurrencySpinner.setAdapter(adapter);

        m3DSSpinner = (AppCompatSpinner) contentView.findViewById(R.id.threeds_spinner);
        ArrayAdapter<CharSequence> adapterAuthSpinner = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_threeds, android.R.layout.simple_spinner_item);
        adapterAuthSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m3DSSpinner.setAdapter(adapterAuthSpinner);
        m3DSSpinner.setSelection(1);

        mPaymentProductsButton = (AppCompatButton) contentView.findViewById(R.id.payment_products_button);
        mPaymentProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnCategories();
            }
        });

        mTimeout = contentView.findViewById(R.id.timeout_editText);
        mTimeout.setText("604800");

        AppCompatSpinner colorSpinner = (AppCompatSpinner) contentView.findViewById(R.id.color_spinner);
        ArrayAdapter<CharSequence> adapterColorSpinner = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_colors_theme, android.R.layout.simple_spinner_dropdown_item);
        adapterColorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapterColorSpinner);
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (inhibit_spinner) {
                    inhibit_spinner = false;
                    return;
                }

                switch (position) {

                    case 0: {
                        makeCustomTheme(
                                R.color.hpf_primary,
                                R.color.hpf_primary_dark,
                                R.color.hpf_light);
                    }
                    break;

                    case 1: {
                        makeCustomTheme(
                                R.color.theme_blue_primary,
                                R.color.theme_blue_primary_dark,
                                R.color.theme_blue_text);
                    }
                    break;

                    case 2: {
                        makeCustomTheme(
                                R.color.theme_green_primary,
                                R.color.theme_green_primary_dark,
                                R.color.theme_green_text);
                    }
                    break;

                    case 3: {
                        makeCustomTheme(
                                R.color.theme_purple_primary,
                                R.color.theme_purple_primary_dark,
                                R.color.theme_purple_text);
                    }
                    break;

                    case 4: {

                        makeCustomTheme(
                                R.color.theme_red_primary,
                                R.color.theme_red_primary_dark,
                                R.color.theme_red_text);
                    }
                    break;
                    case 5: {

                        makeCustomTheme(
                                R.color.theme_yellow_primary,
                                R.color.theme_yellow_primary_dark,
                                R.color.theme_yellow_text);
                    }
                    break;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }

        });

        return contentView;
    }

    private void clickOnEnvironment() {

        Fragment fragment = new EnvironmentFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.demo_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void clickOnCategories() {

        DemoActivity demoActivity = (DemoActivity) getActivity();
        Map<String, Boolean> paymentProducts = demoActivity.getPaymentProducts();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                .replace(R.id.demo_container, ProductCategoryListFragment.newInstance(paymentProducts, customTheme))
                .addToBackStack(null)
                .commit();
    }

    private void makeCustomTheme(int primaryColor, int primaryDarkColor, int textColor) {

        CustomTheme theme = new CustomTheme(primaryColor, primaryDarkColor, textColor);
        switchTheme(theme);
        this.setCustomTheme(theme);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DemoActivity demoActivity = (DemoActivity) getActivity();
        mProgressBar = (ProgressBar) demoActivity.findViewById(R.id.progress);

        mDoneFab = (FloatingActionButton) view.findViewById(R.id.done);
        mDoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.done:

                        if (mRequestQueue != null) {
                            mRequestQueue.cancelAll(TAG);
                        }

                        removeDoneFab(new Runnable() {
                            @Override
                            public void run() {

                                if (getActivity() != null) {
                                    requestSignature();
                                }
                            }
                        });
                        break;
                    default:
                        throw new UnsupportedOperationException(
                                "The onClick method has not been implemented for " + getResources()
                                        .getResourceEntryName(v.getId()));
                }
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /* no-op */
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /* no-op */
            }

            @Override
            public void afterTextChanged(Editable s) {
                // showing the floating action button if input data is valid
                if (isInputDataValid()) {
                    mAmount.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                    mDoneFab.show();
                } else {
                    mAmount.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
                    mDoneFab.hide();
                }
            }
        };

        mAmount = (EditText) view.findViewById(R.id.amountEditText);
        mAmount.addTextChangedListener(textWatcher);

        mCardStorageSwitch.setChecked(ClientConfig.getInstance().isPaymentCardStorageEnabled());
        mCardScanSwitch.setChecked(ClientConfig.getInstance().isPaymentCardScanEnabled());
        mCardNfcScanSwitch.setChecked(ClientConfig.getInstance().isPaymentCardNfcScanEnabled());

        switchTheme(this.getCustomTheme());
    }

    private void requestSignature() {

        String amount = mAmount.getText().toString();
        String currency = (String) mCurrencySpinner.getSelectedItem();

        if (Preferences.isLocalSignature(getContext())) {
            Random random = new Random();
            String orderId = "TEST_" + random.nextInt(100000);

            String signature = getSha1Hex(orderId + amount + currency + Preferences.getLocalSignaturePassword(getContext()));

            DemoActivity activity = (DemoActivity) getActivity();

            final PaymentPageRequest paymentPageRequest = buildPageRequest(activity, orderId);

            if (!TextUtils.isEmpty(mTimeout.getText().toString())) {
                paymentPageRequest.setTimeout(Integer.parseInt(mTimeout.getText().toString()));
            }

            PaymentScreenActivity.start(activity, paymentPageRequest, signature, getCustomTheme());
            mDoneFab.hide();
        } else {
            setLoadingMode(true);

            String url = String.format(getString(R.string.server_url), amount, currency);
            mRequestQueue = Volley.newRequestQueue(getActivity());

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(response.toString(), response.toString());

                            String orderId = null;
                            try {
                                orderId = response.getString("order_id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String signature = null;
                            try {
                                signature = response.getString("signature");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (getActivity() != null) {

                                DemoActivity activity = (DemoActivity) getActivity();
                                if (!TextUtils.isEmpty(orderId) && !TextUtils.isEmpty(signature)) {

                                    final PaymentPageRequest paymentPageRequest = buildPageRequest(activity, orderId);

                                    if (!TextUtils.isEmpty(mTimeout.getText().toString())) {
                                        paymentPageRequest.setTimeout(Integer.parseInt(mTimeout.getText().toString()));
                                    }

                                    PaymentScreenActivity.start(activity, paymentPageRequest, signature, getCustomTheme());
                                    mDoneFab.hide();

                                } else {

                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                    builder.setTitle(R.string.error_title_default)
                                            .setMessage(R.string.unknown_error)
                                            .setNegativeButton(R.string.button_ok, dialogClickListener)
                                            .setCancelable(false)
                                            .show();
                                    showDoneFab();

                                    //DemoActivity demoActivity = (DemoActivity) getActivity();
                                    //ProgressBar progressBar = (ProgressBar) demoActivity.findViewById(R.id.progress);
                                    //progressBar.setVisibility(View.GONE);

                                }
                            }

                            setLoadingMode(false);

                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            if (getActivity() != null) {

                                AppCompatActivity activity = (AppCompatActivity) getActivity();
                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                builder.setTitle(R.string.error_title_default)
                                        .setMessage(R.string.error_body_default)
                                        .setNegativeButton(R.string.button_ok, dialogClickListener)
                                        .setCancelable(false)
                                        .show();
                                showDoneFab();


                            }

                            setLoadingMode(false);
                        }
                    });

            jsObjRequest.setTag(TAG);

            mRequestQueue.add(jsObjRequest);
        }
    }

    private static String getSha1Hex(String clearString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(clearString.getBytes("UTF-8"));
            byte[] bytes = messageDigest.digest();
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes) {
                buffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return buffer.toString();
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isInputDataValid()) {

            showDoneFab();

        } else {

            removeDoneFab(new Runnable() {
                @Override
                public void run() {
                    mDoneFab.hide();
                }
            });
        }

        setLoadingMode(mLoadingMode);
    }

    private void setLoadingMode(boolean loadingMode) {

        if (getActivity() != null) {

            if (loadingMode) {

                mProgressBar.setVisibility(View.VISIBLE);
            } else {

                mProgressBar.setVisibility(View.GONE);
            }
        }

        mLoadingMode = loadingMode;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }

        if (getActivity() != null) {

            mProgressBar.setVisibility(View.GONE);
        }
    }

    protected PaymentPageRequest buildPageRequest(Activity activity, String orderId) {

        PaymentPageRequest paymentPageRequest = new PaymentPageRequest();

        paymentPageRequest.setOrderId(orderId);

        paymentPageRequest.setShortDescription("Un beau vêtement.");
        paymentPageRequest.setLongDescription("Un très beau vêtement en soie de couleur rouge.");

        paymentPageRequest.getCustomer().setFirstname("Martin");
        paymentPageRequest.getCustomer().setLastname("Dupont");
        paymentPageRequest.getCustomer().setEmail("client@domain.com");

        paymentPageRequest.getShippingAddress().setFirstname("Martin");
        paymentPageRequest.getShippingAddress().setLastname("Dupont");

        paymentPageRequest.getCustomer().setRecipientInfo("Employee");
        paymentPageRequest.getCustomer().setStreetAddress("6 Place du Colonel Bourgoin");
        paymentPageRequest.getCustomer().setStreetAddress2("Immeuble de droite");

        paymentPageRequest.getCustomer().setCity("Paris");
        paymentPageRequest.getCustomer().setZipCode("75012");
        paymentPageRequest.getCustomer().setCountry("FR");
        paymentPageRequest.getCustomer().setState("France");

        paymentPageRequest.setTax(2.67f);
        paymentPageRequest.setShipping(1.56f);

        paymentPageRequest.setPaymentCardGroupingEnabled(mGroupCardSwitch.isChecked());

        fillDSP2Information(paymentPageRequest);

        boolean multiUse = mCardStorageSwitch.isChecked();
        paymentPageRequest.setMultiUse(multiUse);
        ClientConfig.getInstance().setPaymentCardStorageEnabled(multiUse);

        ClientConfig.getInstance().setPaymentCardScanEnabled(mCardScanSwitch.isChecked());
        ClientConfig.getInstance().setPaymentCardNfcScanEnabled(mCardNfcScanSwitch.isChecked());

        paymentPageRequest.setAmount(Float.parseFloat(mAmount.getText().toString()));

        String selectedItem = (String) mCurrencySpinner.getSelectedItem();
        paymentPageRequest.setCurrency(selectedItem);

        Integer selectedItemThreeDS = (int) (long) m3DSSpinner.getSelectedItemId() - 1;
        CardTokenPaymentMethodRequest.AuthenticationIndicator authenticationIndicator = CardTokenPaymentMethodRequest.AuthenticationIndicator.fromIntegerValue(selectedItemThreeDS);

        paymentPageRequest.setAuthenticationIndicator(authenticationIndicator);

        DemoActivity demoActivity = (DemoActivity) activity;

        ArrayList<String> productCategories = demoActivity.getPaymentProductsAsList();
        paymentPageRequest.setPaymentProductCategoryList(productCategories);

        return paymentPageRequest;
    }

    private void fillDSP2Information(PaymentPageRequest paymentPageRequest) {
        String merchantRiskStatement = "{\"email_delivery_address\": \"jane.doe@test.com\", \"delivery_time_frame\": 1, \"purchase_indicator\": 1, \"pre_order_date\": 20190925, \"reorder_indicator\": 1, \"shipping_indicator\": 1, \"gift_card\": { \"amount\": 15, \"count\": 0, \"currency\": \"EUR\" } }";
        String previousAuthInfo = "{\"transaction_reference\" : \"800000987283\"}";
        String accountInfo = "{\"customer\": { \"account_change\":20180507, \"opening_account_date\" : 20180507, \"password_change\": 20180507}}";

        paymentPageRequest.setMerchantRiskStatement(merchantRiskStatement);
        paymentPageRequest.setPreviousAuthInfo(previousAuthInfo);
        paymentPageRequest.setAccountInfo(accountInfo);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void switchTheme(CustomTheme customTheme) {

        DemoActivity demoActivity = (DemoActivity) getActivity();

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {

            Window window = demoActivity.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(demoActivity,
                    customTheme.getColorPrimaryDarkId()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), customTheme.getTextColorPrimaryId())));

        } else {
            mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), customTheme.getTextColorPrimaryId()), PorterDuff.Mode.SRC_IN);
        }


        Toolbar toolbar = (Toolbar) demoActivity.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryId()));
        toolbar.setTitleTextColor(ContextCompat.getColor(demoActivity, customTheme.getTextColorPrimaryId()));

        ((TextView) demoActivity.findViewById(R.id.generate_payment_textview)).setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        ((TextView) demoActivity.findViewById(R.id.amount_currency_textview)).setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        ((TextView) demoActivity.findViewById(R.id.threeds_textview)).setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        ((TextView) demoActivity.findViewById(R.id.color_textview)).setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));

        mGroupCardSwitch.setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        mCardStorageSwitch.setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        mCardScanSwitch.setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        mCardNfcScanSwitch.setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        mPaymentProductsButton.setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));

    }

    private void removeDoneFab(@Nullable Runnable endAction) {
        ViewCompat.animate(mDoneFab)
                .scaleX(0)
                .scaleY(0)
                .alpha(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .withEndAction(endAction)
                .start();
    }

    private void showDoneFab() {

        mDoneFab.show();
        mDoneFab.setScaleX(0f);
        mDoneFab.setScaleY(0f);
        ViewCompat.animate(mDoneFab)
                .scaleX(1)
                .scaleY(1)
                .alpha(1)
                .setStartDelay(200)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();
    }

    private boolean isInputDataValid() {

        if (TextUtils.isEmpty(mAmount.getText())) {
            return false;
        }

        try {
            Float.parseFloat(mAmount.getText().toString());
        } catch (NumberFormatException e) {

            return false;
        }

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBundle(CustomTheme.TAG, this.getCustomTheme().toBundle());
        outState.putBoolean(STATE_IS_LOADING, mLoadingMode);
    }

    public CustomTheme getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(CustomTheme customTheme) {
        this.customTheme = customTheme;
    }

}
