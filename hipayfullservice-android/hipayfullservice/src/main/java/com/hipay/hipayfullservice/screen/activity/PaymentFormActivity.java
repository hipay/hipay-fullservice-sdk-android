package com.hipay.hipayfullservice.screen.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.core.models.Transaction;
import com.hipay.hipayfullservice.core.requests.order.PaymentPageRequest;
import com.hipay.hipayfullservice.screen.fragment.PaymentFormFragment;
import com.hipay.hipayfullservice.screen.helper.ApiLevelHelper;
import com.hipay.hipayfullservice.screen.model.CustomTheme;
import com.hipay.hipayfullservice.screen.widget.TextSharedElementCallback;

import java.util.List;

/**
 * Created by nfillion on 29/02/16.
 */
public class PaymentFormActivity extends AppCompatActivity implements PaymentFormFragment.OnCallbackOrderListener {

private static final String TAG = "PaymentProductsActivity";

    private static final String FRAGMENT_TAG = "PaymentForm";

    //private Interpolator mInterpolator;
    //private PaymentProduct mPaymentProduct;
    //private PaymentFormFragment mPaymentFormFragment;
    //private FloatingActionButton mFormFab;
    //private ImageView mIcon;

    private CustomTheme customTheme;
    private boolean mSavedStateIsPlaying;
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
    public void onCallbackOrderReceived(Transaction transaction, Exception exception) {

        if (transaction != null) {

            Intent intent = getIntent();
            intent.putExtra(Transaction.TAG, transaction.toBundle());

            setResult(R.id.transaction_succeed, intent);

        } else {

            //TODO handle exception
        }

        finish();
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


        initToolbar();

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
            Bundle paymentProductBundle = getIntent().getBundleExtra(PaymentProduct.TAG);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.form_fragment_container, PaymentFormFragment.newInstance(paymentPageRequestBundle, paymentProductBundle)).commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar() {

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {

            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this,
                    this.getCustomTheme().getColorPrimaryDarkId()));
        }

        mToolbarBack = (ImageButton) findViewById(R.id.back);
        //mToolbarBack.setBackgroundTintList(ContextCompat.getColor(this,
                //Theme.blue.getTextPrimaryColor()));

        //this.setTheme(Theme.red);

        //this.setToolbarElevation(true);
        mToolbarBack.setColorFilter((ContextCompat.getColor(this,
                getCustomTheme().getTextColorPrimaryId())));

        mToolbarBack.setOnClickListener(mOnClickListener);
        TextView titleView = (TextView) findViewById(R.id.payment_product_title);

        Bundle paymentProductBundle = getIntent().getBundleExtra(PaymentProduct.TAG);
        PaymentProduct paymentProduct = PaymentProduct.fromBundle(paymentProductBundle);

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
        //if (mSavedStateIsPlaying) {
            //mQuizFragment = (QuizFragment) getSupportFragmentManager().findFragmentByTag(
                    //FRAGMENT_TAG);
            //if (!mQuizFragment.hasSolvedStateListener()) {
                //mQuizFragment.setSolvedStateListener(getSolvedStateListener());
            //}
            //findViewById(R.id.quiz_fragment_container).setVisibility(View.VISIBLE);
            //mFormFab.hide();
        //} else {
            //initQuizFragment();
        //}

        //TODO initialize content fragment

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
        //if (mIcon == null || mFormFab == null) {
            //// Skip the animation if icon or fab are not initialized.
            //super.onBackPressed();
            //return;
        //}

        super.onBackPressed();

        //ViewCompat.animate(mToolbarBack)
                //.scaleX(0f)
                //.scaleY(0f)
                //.alpha(0f)
                //.setDuration(100)
                //.setListener(new ViewPropertyAnimatorListenerAdapter() {
                //    @SuppressLint("NewApi")
                //    @Override
                //    public void onAnimationEnd(View view) {
                //        //if (isFinishing() ||
                //                //(ApiLevelHelper.isAtLeast(Build.VERSION_CODES.JELLY_BEAN_MR1)
                //                        //&& isDestroyed())) {
                //            //return;
                //        //}
                //    }
                //})
                //.start();



        // Scale the icon and fab to 0 size before calling onBackPressed if it exists.
        //ViewCompat.animate(mIcon)
                //.scaleX(.7f)
                //.scaleY(.7f)
                //.alpha(0f)
                //.setInterpolator(mInterpolator)
                //.start();

        //ViewCompat.animate(mFormFab)
                //.scaleX(0f)
                //.scaleY(0f)
                //.setInterpolator(mInterpolator)
                //.setStartDelay(100)
                //.setListener(new ViewPropertyAnimatorListenerAdapter() {
                    //@SuppressLint("NewApi")
                    //@Override
                    //public void onAnimationEnd(View view) {
                        //if (isFinishing() ||
                                //(ApiLevelHelper.isAtLeast(Build.VERSION_CODES.JELLY_BEAN_MR1)
                                        //&& isDestroyed())) {
                            //return;
                        //}
                        //PaymentFormActivity.super.onBackPressed();
                    //}
                //})
                //.start();
    }

    public CustomTheme getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(CustomTheme customTheme) {
        this.customTheme = customTheme;
    }

}
