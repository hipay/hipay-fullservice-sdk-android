package com.hipay.hipayfullservice.screen.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.errors.Errors;
import com.hipay.hipayfullservice.core.errors.exceptions.ApiException;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.screen.fragment.AbstractPaymentFormFragment;
import com.hipay.hipayfullservice.screen.helper.ApiLevelHelper;
import com.hipay.hipayfullservice.screen.model.CustomTheme;
import com.hipay.hipayfullservice.screen.widget.TextSharedElementCallback;

import java.net.URL;
import java.util.List;

/**
 * Created by nfillion on 29/02/16.
 */
public class PaymentFormActivity extends AppCompatActivity implements AbstractPaymentFormFragment.OnCallbackOrderListener {

    private static final String TAG = "PaymentProductsActivity";

    private CustomTheme customTheme;
    private ImageButton mToolbarBack;

    public static Intent getStartIntent(Context context, Bundle paymentPageRequestBundle, Bundle themeBundle, PaymentProduct paymentProduct) {

        Intent starter = new Intent(context, PaymentFormActivity.class);

        starter.putExtra(PaymentPageRequest.TAG, paymentPageRequestBundle);

        Bundle productBundle = paymentProduct.toBundle();
        starter.putExtra(PaymentProduct.TAG, productBundle);

        starter.putExtra(CustomTheme.TAG, themeBundle);

        return starter;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //TODO handle the 3DS or other situation

        if (requestCode == PaymentPageRequest.REQUEST_ORDER) {

            if (resultCode == R.id.transaction_succeed) {

                setResult(R.id.transaction_succeed, data);
                finish();

            } else if (resultCode == R.id.transaction_failed) {

                //TODO put exception in there
                setResult(R.id.transaction_failed, null);
                finish();

                //ActivityCompat.finishAfterTransition(this);
                //overridePendingTransition(0, 0);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void manageTransactionStatus(Transaction transaction) {

        switch (transaction.getState()) {

            case TransactionStateCompleted:
            case TransactionStatePending: {

                Intent intent = getIntent();
                intent.putExtra(Transaction.TAG, transaction.toBundle());

                setResult(R.id.transaction_succeed, intent);
                finish();

            } break;

            case TransactionStateDeclined: {

                //TODO failed, he has to retry that.
                //TODO reset the form
                //TODO important to handle.

            } break;

            case TransactionStateForwarding: {

                //TODO handled with forwardUrl
                URL forwardUrl = transaction.getForwardUrl();

                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.form_fragment_container);
                if (fragment != null) {

                    AbstractPaymentFormFragment abstractPaymentFormFragment = (AbstractPaymentFormFragment)fragment;
                    abstractPaymentFormFragment.launchHostedPaymentPage(forwardUrl.toString());
                }

            } break;

            case TransactionStateError: {

                //TODO finally useless. better create an API error

                ApiException exception = new ApiException(getString(R.string.unknown_error),Errors.Code.APIOther.getIntegerValue(), null);
                Intent intent = getIntent();
                intent.putExtra(Errors.TAG, exception.toBundle());
                setResult(R.id.transaction_failed, null);
                finish();

            } break;
        }
    }

    @Override
    public void onCallbackOrderReceived(Transaction transaction, Exception exception) {

        if (transaction != null) {

            this.manageTransactionStatus(transaction);

        } else {

            //TODO handle exception
            Intent intent = getIntent();
            ApiException apiException = (ApiException)exception;
            intent.putExtra(Errors.TAG, apiException.toBundle());
            setResult(R.id.transaction_failed, intent);

            finish();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle customThemeBundle = getIntent().getBundleExtra(CustomTheme.TAG);
        CustomTheme customTheme = CustomTheme.fromBundle(customThemeBundle);

        this.setCustomTheme(customTheme);

        //this.setTheme(this.getCustomTheme().getStyleId());
        setContentView(R.layout.activity_payment_form);

        //mInterpolator = new FastOutSlowInInterpolator();

        Bundle paymentProductBundle = getIntent().getBundleExtra(PaymentProduct.TAG);
        PaymentProduct paymentProduct = PaymentProduct.fromBundle(paymentProductBundle);
        initToolbar(paymentProduct);

        int categoryNameTextSize = getResources()
                .getDimensionPixelSize(R.dimen.payment_product_item_text_size);
        int paddingStart = getResources().getDimensionPixelSize(R.dimen.spacing_double);
        final int startDelay = getResources().getInteger(R.integer.toolbar_transition_duration);
        ActivityCompat.setEnterSharedElementCallback(this,
                new TextSharedElementCallback(categoryNameTextSize, paddingStart) {
                    @Override
                    public void onSharedElementStart(List<String> sharedElementNames,
                                                     List<View> sharedElements,
                                                     List<View> sharedElementSnapshots) {
                        super.onSharedElementStart(sharedElementNames,
                                sharedElements,
                                sharedElementSnapshots);
                        mToolbarBack.setScaleX(0f);
                        mToolbarBack.setScaleY(0f);
                    }

                    @Override
                    public void onSharedElementEnd(List<String> sharedElementNames,
                                                   List<View> sharedElements,
                                                   List<View> sharedElementSnapshots) {
                        super.onSharedElementEnd(sharedElementNames,
                                sharedElements,
                                sharedElementSnapshots);
                        // Make sure to perform this animation after the transition has ended.
                        ViewCompat.animate(mToolbarBack)
                                .setStartDelay(startDelay)
                                .scaleX(1f)
                                .scaleY(1f)
                                .alpha(1f);
                    }
                });

        if (savedInstanceState == null) {

            Bundle paymentPageRequestBundle = getIntent().getBundleExtra(PaymentPageRequest.TAG);
            //Bundle paymentProductBundle = getIntent().getBundleExtra(PaymentProduct.TAG);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.form_fragment_container, AbstractPaymentFormFragment.newInstance(paymentPageRequestBundle, paymentProduct, customThemeBundle)).commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(PaymentProduct paymentProduct) {

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {

            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this,
                    this.getCustomTheme().getColorPrimaryDarkId()));
        }

        mToolbarBack = (ImageButton) findViewById(R.id.back);
        mToolbarBack.setColorFilter((ContextCompat.getColor(this,
                getCustomTheme().getTextColorPrimaryId())));

        mToolbarBack.setOnClickListener(mOnClickListener);
        TextView titleView = (TextView) findViewById(R.id.payment_product_title);


        titleView.setText(paymentProduct.getCode());
        titleView.setTextColor(ContextCompat.getColor(this,
                getCustomTheme().getTextColorPrimaryId()));

        titleView.setBackgroundColor(ContextCompat.getColor(this,
                getCustomTheme().getColorPrimaryId()));

        //if (mSavedStateIsPlaying) {
        //// the toolbar should not have more elevation than the content while playing
        //setToolbarElevation(false);
        //}
    }

    @SuppressLint("NewApi")
    public void setToolbarElevation(boolean shouldElevate) {
        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
            mToolbarBack.setElevation(shouldElevate ?
                    getResources().getDimension(R.dimen.elevation_header) : 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            int i = v.getId();
            if (i == R.id.back) {
                onBackPressed();

            } else {
                throw new UnsupportedOperationException(
                        "OnClick has not been implemented for " + getResources().
                                getResourceName(v.getId()));
            }
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public CustomTheme getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(CustomTheme customTheme) {
        this.customTheme = customTheme;
    }

}
