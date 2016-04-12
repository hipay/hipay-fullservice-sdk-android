package com.hipay.hipayfullservice.screen.activity;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.screen.fragment.PaymentProductsFragment;
import com.hipay.hipayfullservice.screen.helper.ApiLevelHelper;
import com.hipay.hipayfullservice.screen.model.CustomTheme;

/**
 * Created by nfillion on 25/02/16.
 */
public class PaymentProductsActivity extends AppCompatActivity {

    //TODO handle extra user

    private CustomTheme customTheme;

    public static void start(Activity activity, PaymentPageRequest paymentPageRequest, CustomTheme theme) {
        Intent starter = getStartIntent(activity, paymentPageRequest, theme);

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
    static Intent getStartIntent(Context context, PaymentPageRequest paymentPageRequest, CustomTheme theme) {

        Intent starter = new Intent(context, PaymentProductsActivity.class);

        Bundle bundle = paymentPageRequest.toBundle();
        starter.putExtra(PaymentPageRequest.TAG, bundle);

        if (theme == null) {
            theme = new CustomTheme(R.color.hpf_primary,R.color.hpf_primary_dark,R.color.hpf_light);
        }
        starter.putExtra(CustomTheme.TAG, theme.toBundle());

        return starter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PaymentPageRequest.REQUEST_ORDER) {

            if (resultCode == R.id.transaction_succeed) {

                setResult(R.id.transaction_succeed, data);
                finish();

            } else if (resultCode == R.id.transaction_failed) {

                //TODO put exception in there
                setResult(R.id.transaction_failed, data);
                finish();
            }
            else if (resultCode == R.id.transaction_unknown_error) {

                //TODO put exception in there
                setResult(R.id.transaction_unknown_error, null);
                finish();

                //ActivityCompat.finishAfterTransition(this);
                //overridePendingTransition(0, 0);
            }

            // if resultCode == 0, do nothing
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_products);

        Bundle customThemeBundle = getIntent().getBundleExtra(CustomTheme.TAG);
        CustomTheme customTheme = CustomTheme.fromBundle(customThemeBundle);

        this.setCustomTheme(customTheme);


        setUpToolbar();
        if (savedInstanceState == null) {

            attachProductGridFragment();

        } else {

            setProgressBarVisibility(View.GONE);
            //the fragment is loaded already, then remove the loading
        }

        //TODO useful when this activity is gonna be called with makeTransition

        supportPostponeEnterTransition();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpToolbar() {

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {

            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this,
                    this.getCustomTheme().getColorPrimaryDarkId()));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_payment_products);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, this.getCustomTheme().getColorPrimaryId()));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //TODO handle the price (montant)

        //noinspection PrivateResource

        TextView textView = (TextView) toolbar.findViewById(R.id.title);
        textView.setText(R.string.paiment_products_title);
        textView.setTextColor(ContextCompat.getColor(this, this.getCustomTheme().getTextColorPrimaryId()));
    }

    private void attachProductGridFragment() {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.payment_products_container);
        if (!(fragment instanceof PaymentProductsFragment)) {

            Bundle paymentPageRequestBundle = getIntent().getBundleExtra(PaymentPageRequest.TAG);

            Bundle customThemeBundle = getIntent().getBundleExtra(CustomTheme.TAG);

            fragment = PaymentProductsFragment.newInstance(paymentPageRequestBundle, customThemeBundle);

            //fragment.setArguments(paymentPageRequestBundle);
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.payment_products_container, fragment)
                .commit();

        setProgressBarVisibility(View.GONE);
    }

    private void setProgressBarVisibility(int visibility) {
        findViewById(R.id.progress).setVisibility(visibility);
    }

    public CustomTheme getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(CustomTheme customTheme) {
        this.customTheme = customTheme;
    }
}

