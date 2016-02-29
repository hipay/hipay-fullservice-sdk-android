package com.hipay.hipayfullservice.screen.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.hipay.hipayfullservice.R;
import com.hipay.hipayfullservice.core.models.PaymentProduct;
import com.hipay.hipayfullservice.screen.fragment.PaymentFormFragment;
import com.hipay.hipayfullservice.screen.helper.ApiLevelHelper;
import com.hipay.hipayfullservice.screen.model.Theme;
import com.hipay.hipayfullservice.screen.widget.TextSharedElementCallback;

import java.util.List;

/**
 * Created by nfillion on 29/02/16.
 */
public class PaymentFormActivity extends AppCompatActivity {

private static final String TAG = "QuizActivity";
    private static final String IMAGE_CATEGORY = "image_category_";
    private static final String STATE_IS_PLAYING = "isPlaying";
    private static final String FRAGMENT_TAG = "PaymentForm";

    private Interpolator mInterpolator;
    private PaymentProduct mPaymentProduct;
    private PaymentFormFragment mPaymentFormFragment;
    private FloatingActionButton mQuizFab;
    private boolean mSavedStateIsPlaying;
    private ImageView mIcon;
    private Animator mCircularReveal;
    private ObjectAnimator mColorChange;

    private View mToolbarBack;


    public static Intent getStartIntent(Context context, PaymentProduct paymentProduct) {
        Intent starter = new Intent(context, PaymentFormActivity.class);
        //TODO nothing for now
        //starter.putExtra(Category.TAG, category.getId());
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (null != savedInstanceState) {
            mSavedStateIsPlaying = savedInstanceState.getBoolean(STATE_IS_PLAYING);
        }

        mInterpolator = new FastOutSlowInInterpolator();

        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {

            Theme theme = Theme.blue;
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this,
                    theme.getPrimaryDarkColor()));
        }

        setContentView(R.layout.activity_payment_form);
        //noinspection PrivateResource
        mIcon = (ImageView) findViewById(R.id.icon);
        //int resId = getResources().getIdentifier(IMAGE_CATEGORY + categoryId, PaymentProductsAdapter.DRAWABLE,
                //getApplicationContext().getPackageName());


        mIcon.setImageResource(R.drawable.payment_card);

        ViewCompat.animate(mIcon)
                .scaleX(1)
                .scaleY(1)
                .alpha(1)
                .setInterpolator(mInterpolator)
                .setStartDelay(300)
                .start();
        mQuizFab = (FloatingActionButton) findViewById(R.id.fab_quiz);
        mQuizFab.setImageResource(R.drawable.ic_play);
        if (mSavedStateIsPlaying) {
            mQuizFab.hide();
        } else {
            mQuizFab.show();
        }
        mQuizFab.setOnClickListener(mOnClickListener);


        mToolbarBack = findViewById(R.id.back);
        mToolbarBack.setOnClickListener(mOnClickListener);
        TextView titleView = (TextView) findViewById(R.id.category_title);
        //titleView.setText(paymentProduct.getCode());

        titleView.setText("hello world");
        //TODO set the right color
        titleView.setTextColor(ContextCompat.getColor(this,
                Theme.red.getTextPrimaryColor()));

        if (mSavedStateIsPlaying) {
            // the toolbar should not have more elevation than the content while playing
            setToolbarElevation(false);
        }



        //populate(categoryId);
        int categoryNameTextSize = getResources()
                .getDimensionPixelSize(R.dimen.category_item_text_size);
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
            //mQuizFab.hide();
        //} else {
            //initQuizFragment();
        //}

        super.onResume();

    }

    @SuppressLint("NewApi")
    public void setToolbarElevation(boolean shouldElevate) {
        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
            mToolbarBack.setElevation(shouldElevate ?
                    getResources().getDimension(R.dimen.elevation_header) : 0);
        }
    }

    final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            int i = v.getId();
            if (i == 5) {
                //ActivityCompat.finishAfterTransition(QuizActivity.this);

            } else if (i == R.id.back) {
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
        outState.putBoolean(STATE_IS_PLAYING, mQuizFab.getVisibility() == View.GONE);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (mIcon == null || mQuizFab == null) {
            // Skip the animation if icon or fab are not initialized.
            super.onBackPressed();
            return;
        }

        ViewCompat.animate(mToolbarBack)
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setDuration(100)
                .start();

        // Scale the icon and fab to 0 size before calling onBackPressed if it exists.
        ViewCompat.animate(mIcon)
                .scaleX(.7f)
                .scaleY(.7f)
                .alpha(0f)
                .setInterpolator(mInterpolator)
                .start();

        ViewCompat.animate(mQuizFab)
                .scaleX(0f)
                .scaleY(0f)
                .setInterpolator(mInterpolator)
                .setStartDelay(100)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onAnimationEnd(View view) {
                        if (isFinishing() ||
                                (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.JELLY_BEAN_MR1)
                                        && isDestroyed())) {
                            return;
                        }
                        PaymentFormActivity.super.onBackPressed();
                    }
                })
                .start();
    }
}
