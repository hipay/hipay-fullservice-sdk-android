package com.hipay.hipayfullservice.example.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.hipay.hipayfullservice.core.errors.Errors;
import com.hipay.hipayfullservice.core.errors.exceptions.ApiException;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.hipayfullservice.example.DemoActivity;
import com.hipay.hipayfullservice.example.R;
import com.hipay.hipayfullservice.screen.activity.PaymentProductsActivity;
import com.hipay.hipayfullservice.screen.helper.ApiLevelHelper;
import com.hipay.hipayfullservice.screen.model.CustomTheme;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nfillion on 15/03/16.
 */

public class DemoFragment extends Fragment {

    private FloatingActionButton mDoneFab;
    private EditText mAmount;
    private CustomTheme customTheme;

    private SwitchCompat mGroupCardSwitch;
    private SwitchCompat mReusableTokenSwitch;

    private AppCompatSpinner mCurrencySpinner;
    private AppCompatSpinner m3DSSpinner;

    private AppCompatButton mPaymentProductsButton;
    protected boolean inhibit_spinner;

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

                Snackbar snackbar = Snackbar.make(mDoneFab, "Error : " + exception.getLocalizedMessage(),
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
        }

        inhibit_spinner = true;

        this.setCustomTheme(theme);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View contentView = inflater.inflate(R.layout.fragment_demo, container, false);

        mGroupCardSwitch = (SwitchCompat) contentView.findViewById(R.id.group_card_switch);
        mReusableTokenSwitch = (SwitchCompat) contentView.findViewById(R.id.reusable_token_switch);

        mGroupCardSwitch.setTextColor(ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));
        mReusableTokenSwitch.setTextColor(ContextCompat.getColor(getActivity(), customTheme.getColorPrimaryDarkId()));

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
                    } break;

                    case 3: {
                        makeCustomTheme(
                                R.color.theme_purple_primary,
                                R.color.theme_purple_primary_dark,
                                R.color.theme_purple_text);
                    } break;

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

    private void makeCustomTheme(int primaryColor, int primaryDarkColor, int textColor) {

        CustomTheme theme = new CustomTheme(primaryColor, primaryDarkColor, textColor);
        switchTheme(theme);
        this.setCustomTheme(theme);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDoneFab = (FloatingActionButton) view.findViewById(R.id.done);
        mDoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.done:

                        final Activity activity = getActivity();
                        final PaymentPageRequest paymentPageRequest = buildPageRequest(activity);

                        removeDoneFab(new Runnable() {
                            @Override
                            public void run() {

                                PaymentProductsActivity.start(activity, paymentPageRequest, getCustomTheme());
                                mDoneFab.setVisibility(View.INVISIBLE);
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

        mAmount = (EditText)view.findViewById(R.id.amountEditText);
        mAmount.addTextChangedListener(textWatcher);

        switchTheme(this.getCustomTheme());
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
                    mDoneFab.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    protected PaymentPageRequest buildPageRequest(Activity activity) {

        PaymentPageRequest paymentPageRequest = new PaymentPageRequest();

        StringBuilder stringBuilder = new StringBuilder("TEST_SDK_Android_").append(Calendar.getInstance().getTimeInMillis());
        paymentPageRequest.setOrderId(stringBuilder.toString());

        paymentPageRequest.setShortDescription("Un beau vêtement.");
        paymentPageRequest.setLongDescription("Un très beau vêtement en soie de couleur bleue.");

        paymentPageRequest.getCustomer().setFirstname("Martin");
        paymentPageRequest.getCustomer().setLastname("Dupont");
        paymentPageRequest.getCustomer().setEmail("nfillion@hipay.com");

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
        paymentPageRequest.setMultiUse(mReusableTokenSwitch.isChecked());
        paymentPageRequest.setAmount(Float.parseFloat(mAmount.getText().toString()));

        String selectedItem = (String)mCurrencySpinner.getSelectedItem();
        paymentPageRequest.setCurrency(selectedItem);

        Integer selectedItemThreeDS = (int)(long)m3DSSpinner.getSelectedItemId() - 1;
        CardTokenPaymentMethodRequest.AuthenticationIndicator authenticationIndicator = CardTokenPaymentMethodRequest.AuthenticationIndicator.fromIntegerValue(selectedItemThreeDS);

        paymentPageRequest.setAuthenticationIndicator(authenticationIndicator);

        DemoActivity demoActivity = (DemoActivity)activity;

        ArrayList<String> productCategories = demoActivity.getPaymentProductsAsList();
        paymentPageRequest.setPaymentProductCategoryList(productCategories);

        return paymentPageRequest;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void switchTheme(CustomTheme customTheme) {

        DemoActivity demoActivity = (DemoActivity) getActivity();

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {

            Window window = demoActivity.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(demoActivity,
                    customTheme.getColorPrimaryDarkId()));
        }

        Toolbar toolbar = (Toolbar) demoActivity.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryId()));
        toolbar.setTitleTextColor(ContextCompat.getColor(demoActivity, customTheme.getTextColorPrimaryId()));

        ((TextView) demoActivity.findViewById(R.id.generate_payment_textview)).setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        ((TextView) demoActivity.findViewById(R.id.amount_currency_textview)).setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        ((TextView) demoActivity.findViewById(R.id.threeds_textview)).setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        ((TextView) demoActivity.findViewById(R.id.color_textview)).setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));

        mGroupCardSwitch.setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        mReusableTokenSwitch.setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
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

        mDoneFab.setVisibility(View.VISIBLE);
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

    public CustomTheme getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(CustomTheme customTheme) {
        this.customTheme = customTheme;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBundle(CustomTheme.TAG, this.getCustomTheme().toBundle());
    }
}
