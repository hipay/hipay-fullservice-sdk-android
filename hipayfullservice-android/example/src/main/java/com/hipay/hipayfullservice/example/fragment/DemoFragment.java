package com.hipay.hipayfullservice.example.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.core.requests.payment.CardTokenPaymentMethodRequest;
import com.hipay.hipayfullservice.example.R;
import com.hipay.hipayfullservice.screen.activity.PaymentProductsActivity;
import com.hipay.hipayfullservice.screen.model.Theme;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by nfillion on 15/03/16.
 */

public class DemoFragment extends Fragment {

    private static final String ARG_EDIT = "EDIT";
    private FloatingActionButton mDoneFab;
    private EditText mAmount;

    public static DemoFragment newInstance(boolean edit) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_EDIT, edit);
        DemoFragment fragment = new DemoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Snackbar snackbar = Snackbar.make(mDoneFab, "Transaction succeed",
                Snackbar.LENGTH_INDEFINITE);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor((ContextCompat.getColor(getActivity(),
                Theme.blue.getPrimaryColor())));
        snackbar.show();

        if (requestCode == PaymentPageRequest.REQUEST_ORDER && resultCode == R.id.transaction_succeed) {
            //TODO let's find transaction

            Bundle bundle = data.getBundleExtra("hello");
            PaymentPageRequest paymentPageRequest = PaymentPageRequest.fromBundle(bundle);

            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //.setAction("Action", null).(Color.GREEN).show();

        }

        super.onActivityResult(requestCode, resultCode, data);
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
        PaymentProductsActivity.start(activity, hardPageRequest());
    }

    protected PaymentPageRequest hardPageRequest() {

        PaymentPageRequest paymentPageRequest = new PaymentPageRequest();

        paymentPageRequest.setAmount(225.f);
        paymentPageRequest.setCurrency("EUR");

        StringBuilder stringBuilder = new StringBuilder("TEST_SDK_IOS_").append(Calendar.getInstance().getTimeInMillis()/1000);

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
}
