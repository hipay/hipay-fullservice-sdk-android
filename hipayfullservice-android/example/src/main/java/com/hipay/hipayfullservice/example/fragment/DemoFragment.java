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

import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.hipayfullservice.example.DemoActivity;
import com.hipay.hipayfullservice.example.R;
import com.hipay.hipayfullservice.screen.activity.PaymentProductsActivity;
import com.hipay.hipayfullservice.screen.helper.ApiLevelHelper;
import com.hipay.hipayfullservice.screen.model.CustomTheme;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by nfillion on 15/03/16.
 */

public class DemoFragment extends Fragment {

    private FloatingActionButton mDoneFab;
    private EditText mAmount;
    private CustomTheme customTheme;

    public static DemoFragment newInstance() {

        DemoFragment fragment = new DemoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        CustomTheme theme = new CustomTheme(
                R.color.hpf_primary,
                R.color.hpf_primary_dark,
                R.color.theme_blue_text);

        this.setCustomTheme(theme);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PaymentPageRequest.REQUEST_ORDER) {

            if (resultCode == R.id.transaction_succeed) {

                //TODO let's find transaction

                Bundle transactionBundle = data.getBundleExtra(Transaction.TAG);
                Transaction transaction = Transaction.fromBundle(transactionBundle);

                Snackbar snackbar = Snackbar.make(mDoneFab, "Transaction succeed",
                        Snackbar.LENGTH_INDEFINITE);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor((ContextCompat.getColor(getActivity(),
                        R.color.colorAccent)));
                snackbar.show();

            } else if (resultCode == R.id.transaction_failed) {

                //TODO let's find transaction

                Snackbar snackbar = Snackbar.make(mDoneFab, "Transaction succeed",
                        Snackbar.LENGTH_INDEFINITE);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor((ContextCompat.getColor(getActivity(),
                        R.color.colorAccent)));
                snackbar.show();

            } else {

            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View contentView = inflater.inflate(R.layout.fragment_demo, container, false);

        AppCompatSpinner spinner = (AppCompatSpinner) contentView.findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AppCompatSpinner threedsSpinner = (AppCompatSpinner) contentView.findViewById(R.id.threeds_spinner);
        ArrayAdapter<CharSequence> adapterAuthSpinner = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_threeds, android.R.layout.simple_spinner_item);
        adapterAuthSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        threedsSpinner.setAdapter(adapterAuthSpinner);

        AppCompatSpinner colorSpinner = (AppCompatSpinner) contentView.findViewById(R.id.color_spinner);
        ArrayAdapter<CharSequence> adapterColorSpinner = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_colors_theme, android.R.layout.simple_spinner_dropdown_item);
        adapterColorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapterColorSpinner);
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {

                        CustomTheme theme = new CustomTheme(
                                R.color.hpf_primary,
                                R.color.hpf_primary_dark,
                                R.color.theme_blue_text);

                        switchTheme(theme);
                        DemoFragment.this.setCustomTheme(theme);
                    }
                    break;

                    case 1: {

                        // CustomTheme theme = new CustomTheme(
                        //         R.color.theme_yellow_primary,
                        //         R.color.theme_yellow_primary_dark,
                        //         R.color.theme_yellow_background,
                        //         R.color.theme_yellow_text,
                        //         R.color.theme_yellow_accent);
                        // switchTheme(theme);
                        // DemoFragment.this.setCustomTheme(theme);

                        CustomTheme theme = new CustomTheme(
                                R.color.theme_red_primary,
                                R.color.theme_red_primary_dark,
                                R.color.theme_red_text);
                        switchTheme(theme);
                        DemoFragment.this.setCustomTheme(theme);
                    }
                    break;

                    case 3: {

                        CustomTheme theme = new CustomTheme(
                                R.color.theme_green_primary,
                                R.color.theme_green_primary_dark,
                                R.color.theme_green_text);

                        switchTheme(theme);
                        DemoFragment.this.setCustomTheme(theme);
                    } break;

                    case 2: {

                        CustomTheme theme = new CustomTheme(
                                R.color.theme_purple_primary,
                                R.color.theme_purple_primary_dark,
                                R.color.theme_purple_text);

                        switchTheme(theme);
                        DemoFragment.this.setCustomTheme(theme);

                    } break;

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }

        });

        return contentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mDoneFab = (FloatingActionButton) view.findViewById(R.id.done);
        mDoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.done:
                        removeDoneFab(new Runnable() {
                            @Override
                            public void run() {
                                validateCartWithTransition();
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
                // hiding the floating action button if text is empty
                if (s.length() == 0) {
                    mDoneFab.hide();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // showing the floating action button if avatar is selected and input data is valid
                if (isInputDataValid()) {
                    mDoneFab.show();
                }
            }
        };

        mAmount = (EditText)view.findViewById(R.id.amountEditText);
        mAmount.addTextChangedListener(textWatcher);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        showDoneFab();
    }

    private void validateCartWithTransition() {

        final Activity activity = getActivity();
        PaymentProductsActivity.start(activity, hardPageRequest(), this.getCustomTheme());
    }

    protected PaymentPageRequest hardPageRequest() {

        PaymentPageRequest paymentPageRequest = new PaymentPageRequest();

        paymentPageRequest.setAmount(225.f);
        paymentPageRequest.setCurrency("EUR");

        StringBuilder stringBuilder = new StringBuilder("TEST_SDK_Android_").append(Calendar.getInstance().getTimeInMillis()/1000);

        paymentPageRequest.setOrderId(stringBuilder.toString());
        paymentPageRequest.setShortDescription("Outstanding item");
        paymentPageRequest.getCustomer().setCountry("FR");
        paymentPageRequest.getCustomer().setFirstname("John");
        paymentPageRequest.getCustomer().setLastname("Doe");
        paymentPageRequest.setPaymentCardGroupingEnabled(true);
        paymentPageRequest.setMultiUse(true);
        paymentPageRequest.setPaymentProductCategoryList(

                Arrays.asList(

                        PaymentProduct.PaymentProductCodeCB,
                        PaymentProduct.PaymentProductCodeMasterCard,
                        PaymentProduct.PaymentProductCodeVisa,
                        PaymentProduct.PaymentProductCodeAmericanExpress,
                        PaymentProduct.PaymentProductCodeMaestro,
                        PaymentProduct.PaymentProductCodeDiners
                )
        );

        paymentPageRequest.getCustomer().setEmail("nfillion@hipay.com");

        paymentPageRequest.setAuthenticationIndicator(CardTokenPaymentMethodRequest.AuthenticationIndicator.AuthenticationIndicatorUndefined);

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

        ((SwitchCompat) demoActivity.findViewById(R.id.group_card_switch)).setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));
        ((SwitchCompat) demoActivity.findViewById(R.id.reusable_token_switch)).setTextColor(ContextCompat.getColor(demoActivity, customTheme.getColorPrimaryDarkId()));

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

        return !TextUtils.isEmpty(mAmount.getText());
    }

    public CustomTheme getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(CustomTheme customTheme) {
        this.customTheme = customTheme;
    }
}
