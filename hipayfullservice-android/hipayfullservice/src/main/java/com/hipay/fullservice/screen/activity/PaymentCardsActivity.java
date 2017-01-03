package com.hipay.fullservice.screen.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.TextView;

import com.hipay.fullservice.R;
import com.hipay.fullservice.core.client.GatewayClient;
import com.hipay.fullservice.core.models.PaymentCardToken;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.screen.fragment.PaymentCardsFragment;
import com.hipay.fullservice.screen.helper.ApiLevelHelper;
import com.hipay.fullservice.screen.model.CustomTheme;

import java.util.ArrayList;

/**
 * Created by nfillion on 21/12/2016.
 */

public class PaymentCardsActivity extends PaymentScreenActivity implements PaymentCardsFragment.OnPaymentCardSelectedListener {

    private CustomTheme customTheme;

    public static void start(Activity activity, PaymentPageRequest paymentPageRequest, String signature, CustomTheme theme) {
        Intent starter = getStartIntent(activity, paymentPageRequest, signature, theme);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, null);

        ActivityCompat.startActivityForResult(activity, starter, PaymentPageRequest.REQUEST_ORDER, activityOptions.toBundle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //no-op
    }

    @Override
    protected void onStop() {
        super.onStop();
        //no-op
    }

    @NonNull
    static Intent getStartIntent(Context context, PaymentPageRequest paymentPageRequest, String signature, CustomTheme theme) {

        Intent starter = new Intent(context, PaymentCardsActivity.class);

        Bundle bundle = paymentPageRequest.toBundle();
        starter.putExtra(PaymentPageRequest.TAG, bundle);

        if (theme == null) {
            theme = new CustomTheme(R.color.hpf_primary,R.color.hpf_primary_dark,R.color.hpf_light);
        }
        starter.putExtra(CustomTheme.TAG, theme.toBundle());
        starter.putExtra(GatewayClient.SIGNATURE_TAG, signature);

        return starter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PaymentPageRequest.REQUEST_ORDER) {

            if (resultCode == R.id.transaction_succeed) {

                setResult(R.id.transaction_succeed, data);
                finish();

            } else if (resultCode == R.id.transaction_failed) {

                setResult(R.id.transaction_failed, data);
                finish();

            } else {

                //Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.payment_cards_container);
                //if (fragment != null) {

                    //PaymentProductsFragment paymentProductsFragment = (PaymentProductsFragment) fragment;
                    //List<PaymentProduct> paymentProducts = paymentProductsFragment.getPaymentProducts();
                    //if (paymentProducts == null || paymentProducts.isEmpty()) {
                        ////finish();
                    //}
                //}
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_cards);

        Bundle customThemeBundle = getIntent().getBundleExtra(CustomTheme.TAG);
        CustomTheme customTheme = CustomTheme.fromBundle(customThemeBundle);

        this.setCustomTheme(customTheme);

        setUpToolbar();
        if (savedInstanceState == null) {

            attachPaymentCardsListFragment();
        }

        //useful when this activity is gonna be called with makeTransition
        //supportPostponeEnterTransition();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpToolbar() {

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {

            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this,
                    this.getCustomTheme().getColorPrimaryDarkId()));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_payment_cards);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, this.getCustomTheme().getColorPrimaryId()));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView textView = (TextView) toolbar.findViewById(R.id.title);
        textView.setText(R.string.payment_cards_title);
        textView.setTextColor(ContextCompat.getColor(this, this.getCustomTheme().getTextColorPrimaryId()));
    }

    private void attachPaymentCardsListFragment() {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.payment_cards_container);
        if (!(fragment instanceof PaymentCardsFragment)) {

            Bundle paymentPageRequestBundle = getIntent().getBundleExtra(PaymentPageRequest.TAG);
            Bundle customThemeBundle = getIntent().getBundleExtra(CustomTheme.TAG);
            String signature = getIntent().getStringExtra(GatewayClient.SIGNATURE_TAG);

            Bundle args = new Bundle();
            args.putBundle(PaymentPageRequest.TAG, paymentPageRequestBundle);
            args.putBundle(CustomTheme.TAG, customThemeBundle);
            args.putString(GatewayClient.SIGNATURE_TAG, signature);

            fragment = PaymentCardsFragment.newInstance(args);
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.payment_cards_container, fragment)
                .commit();
    }

    public CustomTheme getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(CustomTheme customTheme) {
        this.customTheme = customTheme;
    }

    @Override
    public void onPaymentCardSelected(int position, boolean isChecked) {

    }
}
